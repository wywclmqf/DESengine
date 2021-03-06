package De.Hpi.Disco.Source.input;

import De.Hpi.Disco.Source.utility.DistributedChild;
import De.Hpi.Disco.Source.utility.DistributedUtils;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class InputStream implements Runnable {

    protected final int streamId;
    private final InputStreamConfig config;
    private final String nodeIp;
    private final int nodePort;
    private EventGenerator eventGenerator;

    public InputStream(int streamId, InputStreamConfig config, String nodeIp, int nodePort, EventGenerator eventGenerator) {
        this.streamId = streamId;
        this.config = config;
        this.nodeIp = nodeIp;
        this.nodePort = nodePort;
        this.eventGenerator = eventGenerator;
    }

    protected String streamName() { return this.eventGenerator.getClass().getSimpleName(); }

    @Override
    public void run() {
        System.out.println(this.streamIdString("Starting " + this.streamName() + " with " + this.config.numEventsToSend
                + " events to node " + this.nodeIp + ":" + this.nodePort + " with " + this.config));

        try (ZContext context = new ZContext()) {
            final long sendingStartTime = this.registerAtNode(context);

            Thread.sleep(1000);

            ZMQ.Socket eventSender = context.createSocket(SocketType.PUSH);
            eventSender.setHWM(1000);
            eventSender.connect(DistributedUtils.buildTcpUrl(this.nodeIp, this.nodePort));

            System.out.println(this.streamIdString("Start sending data."));

            long lastEventTimestamp = this.eventGenerator.generateAndSendEvents(eventSender);
            final long duration = System.currentTimeMillis() - sendingStartTime;
            eventSender.sendMore(DistributedUtils.STREAM_END);
            eventSender.send(String.valueOf(this.streamId));
            System.out.println(this.streamIdString("Last event timestamp: " + lastEventTimestamp +
                    " (total sending duration: " + duration + "ms)"));

            // Allow stream end to be processed before killing the context
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println(this.streamIdString("Finished sending events. Shutting down..."));
    }


    protected long registerAtNode(ZContext context) {
        System.out.println(this.streamIdString("Registering at node."));
        final ZMQ.Socket nodeRegistrar = context.createSocket(SocketType.REQ);
        nodeRegistrar.connect(DistributedUtils.buildTcpUrl(this.nodeIp, this.nodePort + DistributedChild.STREAM_REGISTER_PORT_OFFSET));

        nodeRegistrar.send(String.valueOf(this.streamId));
        long startTime =  Long.parseLong(nodeRegistrar.recvStr());
        System.out.println(this.streamIdString("Registering successful."));
        return startTime;
    }

    private String streamIdString(String msg) {
        return "[STREAM-" + this.streamId + "] " + msg;
    }


}
