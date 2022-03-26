package De.Hpi.DesisCen.RootNode.BaselineCentral;//package RootNode.BaselineCentral;
//import Configure.Configuration;
//import Dao.Query;
//import Dao.Tuple;
//import Dao.Window;
//import Generator.QueryGenerator;
//import RootNode.PrintResult;
//
//import java.util.ArrayList;
//import java.util.concurrent.ConcurrentLinkedQueue;
//
//
//public class BaselineCentralRootNode {
//
//    private Configuration conf;
//    private QueryGenerator queryGenerator;
//    private ConcurrentLinkedQueue<Query> queryQueue;
//    private ConcurrentLinkedQueue<Query> queryList;
//    private ConcurrentLinkedQueue<Window> resultQueue;
//    private ConcurrentLinkedQueue<Window> resultFromIntermedia;
//    private ConcurrentLinkedQueue<Tuple> dataQueue;
//
//
//    private ArrayList<Thread> threadsList;
//
//    public BaselineCentralRootNode(Configuration conf, int nodeId){
//        this.conf = conf;
//        this.conf.setNodeId(nodeId);
//        this.threadsList = new ArrayList<>();
//        this.queryQueue = new ConcurrentLinkedQueue<Query>();
//        this.queryList = new ConcurrentLinkedQueue<Query>();
//        this.resultQueue = new ConcurrentLinkedQueue<Window>();
//        this.resultFromIntermedia = new ConcurrentLinkedQueue<Window>();
//        this.dataQueue = new ConcurrentLinkedQueue<Tuple>();
//        this.queryGenerator =new QueryGenerator(queryQueue, queryList, conf);
//
//
//        initialRootNode();
//        startRootNode();
//    }
//
//    public void initialRootNode(){
//
//        //generate query
//        queryGenerator.generate();
//
//        threadsList.add(new Thread(new BaselineCentralReceiver(dataQueue, conf)));
//        threadsList.add(new Thread(new BaselineCentraComputation(conf, resultQueue, queryQueue, dataQueue)));
//        threadsList.add(new Thread(new PrintResult(resultQueue, conf)));
//
//    }
//
//    public void startRootNode(){
//        threadsList.forEach( thread -> thread.start());
//    }
//
//    public void stopRootNode(){
//        threadsList.forEach( thread -> thread.interrupt());
//    }

//}
