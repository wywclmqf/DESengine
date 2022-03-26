package De.Hpi.Desis.MessageManager;

import De.Hpi.Desis.Configure.Configuration;
import De.Hpi.Desis.Dao.Window;
import De.Hpi.Desis.Message.MessageResult;
import org.msgpack.MessagePack;
import org.zeromq.ZMQ;

import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

public class IntermediatePublishMessage implements Runnable{

    private Configuration conf;
    private ConcurrentLinkedQueue<Window> resultQueue;
    private ZMQ.Socket socketPub;

    public IntermediatePublishMessage(ConcurrentLinkedQueue<Window> resultQueue, ZMQ.Socket socketPub, Configuration conf) {
        this.conf = conf;
        this.resultQueue =resultQueue;
        this.socketPub = socketPub;
    }

    public void run() {
//        System.out.println("Starting UpperRequestThread ----intermediateNode");
        MessagePack msgpack = new MessagePack();
        long endtime = System.currentTimeMillis();
        while (true) {
            if(!resultQueue.isEmpty()) {
                Window window = resultQueue.poll();
                window.setNodeId(conf.getNodeId());
                //the message type now it the data
                MessageResult resultFromIntermediaToRoot = new MessageResult();
                resultFromIntermediaToRoot.setNodeId(conf.getNodeId());
                resultFromIntermediaToRoot.setMessageType(conf.MESSAGERESULT);
                resultFromIntermediaToRoot.setMessageLevel(conf.INTERMEDIATENODEMESSAGE);
                resultFromIntermediaToRoot.window = window;
                try {
                    byte[] raw = msgpack.write(resultFromIntermediaToRoot);
                    socketPub.send(raw);
                    if(conf.DEBUGMODE) {
                        if (System.currentTimeMillis() - endtime > conf.BenchMarkDebugFrequency) {
                            endtime = System.currentTimeMillis();
                            System.out.println("InteNode--" + resultFromIntermediaToRoot.getNodeId() + "--Process--" +
                                    +resultFromIntermediaToRoot.window.getWindowId()
                                    + "  QueryId:  " + resultFromIntermediaToRoot.window.getQueryId()
//                            + "  function  " + resultFromLocalToIntermedia.window.getFunction()
//                            + "  windowType  " + resultFromLocalToIntermedia.window.getWindowType()
                                    + "  result:  " + resultFromIntermediaToRoot.window.result
                                    + "  count:  " + resultFromIntermediaToRoot.window.count
                                    + "  listSize:  " + resultFromIntermediaToRoot.window.tuples.size()
//                                    + "  NetworkOverhead:  " + networkOverhead
//                                    + "  Throughput:  " + resultFromIntermediaToRoot.window.tupleCounter / ((endtime - begintime) / 1000.0)
                            );
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
//    private static long getNetworkOverhead(int rawSize) {
//        return (rawSize / (9000 - 46) + 1) * 46 + (45 + 45)  + (44 + 44 + 44)  * 2 + rawSize;
//    }

}
