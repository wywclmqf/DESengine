package De.Hpi.Disco.Source.utility;

import De.Hpi.Disco.Source.merge.FinalWindowsAndSessionStarts;
import org.zeromq.ZMQ;

import static De.Hpi.Disco.Source.utility.DistributedUtils.*;

public class DistributedMergeNode implements Runnable {
    private final static String NODE_IDENTIFIER = "MERGER";

    private final DistributedNode nodeImpl;

    //for debug
    private int latencyOverall;
    private int latencyCounter;

    public DistributedMergeNode(String parentIp, int parentControllerPort, int parentWindowPort,
            int controllerPort, int windowPort, int numChildren, int nodeId) {
        this.nodeImpl = new DistributedNode(nodeId, NODE_IDENTIFIER, controllerPort, windowPort, numChildren,
                parentIp, parentControllerPort, parentWindowPort);

        //for debug
        this.latencyOverall = 0;
        this.latencyCounter = 0;

        nodeImpl.createDataPuller();
        nodeImpl.createWindowPusher(parentIp, parentWindowPort);
    }

    @Override
    public void run() {
        System.out.println(nodeImpl.nodeString("Starting merge worker with window port " + nodeImpl.dataPort +
                " and controller port " + nodeImpl.controllerPort + ", with " + nodeImpl.numChildren +
                " children. Connecting to parent at " + nodeImpl.parentIp + " with controller port " +
                nodeImpl.parentControllerPort + " and window port " + nodeImpl.parentWindowPort));

        try {
            nodeImpl.registerAtParent();
            nodeImpl.waitForChildren();
            processPreAggregatedWindows();
        } finally {
            nodeImpl.close();
        }
    }

    private void processPreAggregatedWindows() {
        ZMQ.Socket streamInput = nodeImpl.dataPuller;
        System.out.println(nodeImpl.nodeString("Waiting for window data."));

        while (!nodeImpl.isInterrupted()) {
            String windowOrStreamEnd = streamInput.recvStr();
            if (windowOrStreamEnd == null) {
                continue;
            }

            if (windowOrStreamEnd.equals(STREAM_END)) {
                if (!nodeImpl.isTotalStreamEnd()) {
                    continue;
                }

                System.out.println(nodeImpl.nodeString("No more children. Shutting down..."));
                nodeImpl.endChild();
                return;
            }

            if (windowOrStreamEnd.equals(CONTROL_STRING)) {
                FinalWindowsAndSessionStarts registerResults = nodeImpl.handleControlInput();
                nodeImpl.sendPreAggregatedWindowsToParent(registerResults.getFinalWindows());
                nodeImpl.sendSessionStartsToParent(registerResults.getNewSessionStarts());
                continue;
            }

            if (windowOrStreamEnd.equals(EVENT_STRING)) {
                // Simply pass on event for count-based window.
                nodeImpl.forwardEvent(windowOrStreamEnd);
                continue;
            }
            long latencyStart = System.nanoTime();
            nodeImpl.processAndSendWindowAggregates();
            long latencyEnd = System.nanoTime();
            latencyOverall += (int)(latencyEnd-latencyStart);
            latencyCounter++;
            System.out.println("Inter - latency  " + latencyOverall/latencyCounter);

        }
    }
}
