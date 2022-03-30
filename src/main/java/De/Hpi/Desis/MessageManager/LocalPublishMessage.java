package De.Hpi.Desis.MessageManager;

import De.Hpi.Desis.Configure.Configuration;
import De.Hpi.Desis.Dao.Window;
import De.Hpi.Desis.Dao.WindowCollection;
import De.Hpi.Desis.Message.MessageResult;
import org.msgpack.MessagePack;
import org.zeromq.ZMQ;

import java.io.IOException;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LocalPublishMessage implements Runnable{

    private Configuration conf;
    private ConcurrentLinkedQueue<WindowCollection> intermediateResultQueue;
    private ZMQ.Socket socketPub;

    public LocalPublishMessage(ConcurrentLinkedQueue<WindowCollection> intermediateResultQueue, ZMQ.Socket socketPub, Configuration conf) {
        this.conf =conf;
        this.intermediateResultQueue =intermediateResultQueue;
        this.socketPub = socketPub;
    }

    public void run() {
////        System.out.println("Starting RequestThread ----localNode");
//        MessagePack msgpack = new MessagePack();
////        Long networkOverhead = 0l;
////        long begintime = System.currentTimeMillis();
//        long endtime = System.currentTimeMillis();
//        while (true) {
//            if(!intermediateResultQueue.isEmpty()) {
//                Window window = (Window) intermediateResultQueue.poll();
//                window.nodeId = conf.getNodeId();
//                //the message type now it the data
//                MessageResult messageResult = new MessageResult();
//                messageResult.setNodeId(conf.getNodeId());
//                messageResult.setMessageType(conf.MESSAGERESULT);
//                messageResult.setMessageLevel(conf.LOCALNODEMESSAGE);
//                messageResult.window = window;
//                try {
//                    byte[] raw = msgpack.write(messageResult);
//                    socketPub.send(raw);
//                    if(conf.DEBUGMODE) {
////                        networkOverhead += getNetworkOverhead(raw.length);
//                        if (System.currentTimeMillis() - endtime > conf.BenchMarkDebugFrequency) {
////                        if (System.currentTimeMillis() - endtime > 1000000) {
//                                endtime = System.currentTimeMillis();
//                                System.out.println("localNode--" + messageResult.getNodeId() + "--Process--"
//                                        + Arrays.toString(messageResult.window.queryIdList)
//                                        + "  QueryId:  " + messageResult.window.queryId
////                            + "  function  " + resultFromLocalToIntermedia.window.getFunction()
////                            + "  windowType  " + resultFromLocalToIntermedia.window.getWindowType()
//                                        + "  result:  " + messageResult.window.result
//                                        + "  count:  " + messageResult.window.count
//                                        + "  listSize:  " + messageResult.window.tuples.size()
////                                        + "  Throughput:  " + messageResult.window.tupleCounter / ((endtime - begintime) / 1000.0)
////                                        + "  NetworkOverhead:  " + networkOverhead
////                                        + "  Allcounter:  " + messageResult.window.tupleCounter
////                                        + "  Time:  " + (endtime - begintime) / 1000.0
////                                        + "  GCTime:  " + getGarbageCollectionTime()
////                                        + "  GC/Time:  " + (double) getGarbageCollectionTime() / (endtime - begintime)
//                                );
//
//                            }
//                        }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }

//    private static long getGarbageCollectionTime() {
//        long collectionTime = 0;
//        for (GarbageCollectorMXBean garbageCollectorMXBean : ManagementFactory.getGarbageCollectorMXBeans()) {
//            collectionTime += garbageCollectorMXBean.getCollectionTime();
//        }
//        return collectionTime;
//    }
//
//    private static long getNetworkOverhead(int rawSize) {
//        return (rawSize / (9000 - 46) + 1) * 46 + (45 + 45) + rawSize;
//    }
}
