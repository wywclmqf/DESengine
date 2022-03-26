package De.Hpi.DesisCen.LocalNode.BaselineCentral;//package LocalNode.BaselineCentral;
//
//import Configure.Configuration;
//import Dao.Query;
//import Dao.Tuple;
//import Dao.Window;
//import Generator.DataGenerator;
//import LocalNode.BaselineNoOptimizer.BaselineNOComputationEngine;
//import MessageManager.LocalPublishMessage;
//import MessageManager.LocalSubscribeMessage;
//import org.zeromq.SocketType;
//import org.zeromq.ZContext;
//import org.zeromq.ZMQ;
//
//import java.util.ArrayList;
//import java.util.concurrent.ConcurrentLinkedQueue;
//
//public class BaselineCentralLocalNode {
//
//    private Configuration conf;
//    private ConcurrentLinkedQueue<Query> queryQueue;
//    private ConcurrentLinkedQueue<Window> intermediateResultQueue;
//    private ConcurrentLinkedQueue<ArrayList<Tuple>> dataQueue;
//
//    private ArrayList<Thread> threadsList;
//
//    public BaselineCentralLocalNode(Configuration conf, int nodeId){
//        this.conf = conf;
//        this.conf.setNodeId(nodeId);
//        this.threadsList = new ArrayList<>();
//        this.queryQueue = new ConcurrentLinkedQueue<Query>();
//        this.intermediateResultQueue = new ConcurrentLinkedQueue<Window>();
//        this.dataQueue = new ConcurrentLinkedQueue<ArrayList<Tuple>>();
//
//
//        initialLocalode();
//        startLocalNode();
//    }
//
//    public void initialLocalode() {
//
//        //sender
////        threadsList.add(new Thread(new BaselineCentralSender(dataQueue, conf)));
//
//        //generate data
//        threadsList.add(new Thread(new DataGenerator(conf, dataQueue)));
//
//    }
//
//    public void startLocalNode(){
//        threadsList.forEach( thread -> thread.start());
//    }
//
//    public void stopLocalNode(){
//        threadsList.forEach( thread -> thread.interrupt());
//    }
//
//}
