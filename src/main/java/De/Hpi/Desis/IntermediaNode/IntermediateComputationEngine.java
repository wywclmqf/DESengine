package De.Hpi.Desis.IntermediaNode;

import De.Hpi.Desis.Configure.Configuration;
import De.Hpi.Desis.Dao.*;
import org.apache.commons.collections.ArrayStack;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;


public class IntermediateComputationEngine implements Runnable {

    private Configuration conf;
    private ConcurrentLinkedQueue<WindowCollection> resultQueueFromLocal;
    private ConcurrentLinkedQueue<WindowCollection> resultQueue;
    private ConcurrentLinkedQueue<Query> queryQueue;
    private ArrayList<IntermediateTask> intermediateTasks;
    private ArrayList<Tuple> tupleListForCen;
    private long tupleBatchCounter;

     IntermediateComputationEngine(ConcurrentLinkedQueue<WindowCollection> resultQueue, ConcurrentLinkedQueue<WindowCollection> resultQueueFromLocal,
                                   ConcurrentLinkedQueue<Query> queryQueue, Configuration conf){
         this.conf = conf;
         this.resultQueueFromLocal =resultQueueFromLocal;
         this.resultQueue = resultQueue;
         this.queryQueue = queryQueue;
         this.intermediateTasks = new ArrayStack();
         this.tupleListForCen = new ArrayList<>();
         this.tupleBatchCounter = tupleBatchCounter;
    }

    public void run() {
        while (true) {
            if (!resultQueueFromLocal.isEmpty()) {
                //to read all queries
                queryPreProcess();

                //get intermediate result from local nodes
                WindowCollection windowCollection = resultQueueFromLocal.poll();
                long timeTemp = System.currentTimeMillis();

                windowProcess(windowCollection, timeTemp);
            }
        }
    }

    //can not process duplicate windows
    private void windowProcess(WindowCollection windowCollection, long timeTemp){
//        //local window
//        //the all scenarios are,
//        // 1)empty, 2)less window arrived, put it into list
//        // 3) all window arrived 4)expired, process and send them all
//        // 5) old window arrived throw
//        // 6) disorder window, keep it
//
//        //the windows that have same window ids should be in a same intermediate window
//        //and the intermediate window is to collect same windowid windows that from different nodes.

        WindowCollection newWindowCollection = new WindowCollection();
        newWindowCollection.windowList = new ArrayList<>();
        newWindowCollection.tuples = new ArrayList<>();
        //record central windows
        ArrayList<Window> windowListForCen = new ArrayList<>();
        //if there is median or quantile window 1) end or 2) expired 3) process
        boolean[] windowFlag = {false, false, false};

        windowCollection.windowList.forEach(window -> {
            //decentralized
            if(intermediateTasks.get(window.queryId).query.getScenario() == conf.DeCentralizedAggregation) {
                //5) old window arrived throw, or find aim intermediate window
                if (intermediateTasks.get(window.queryId).getWindowCounter() <= window.windowId) {
                    //if we need to build a new window
                    boolean isNewWindow = true;
                    //clean the expired intermediate windows, and find the aim one
                    Iterator<IntermediateWindow> iter = intermediateTasks.get(window.queryId).intermediateWindows.iterator();
                    while (iter.hasNext()) {
                        IntermediateWindow intermediateWindow = iter.next();
                        //4)expired, process and send them all
                        if (timeTemp - intermediateWindow.getProcessTime() > conf.EXPIREDTIME) {
                            //send it
                            newWindowCollection.windowList.add(intermediateWindow.window);
                            //update task
                            iter.remove();
                            intermediateTasks.get(window.queryId).windowCounterAdd();
                        } else if (intermediateWindow.getWindowId() == window.windowId) {
                            //we find aim intermediate window
                            //this is not first window
                            intermediateWindow.deleteWindowWaitingCounter();
                            mergeWindow(intermediateTasks.get(window.queryId), intermediateWindow.window, window);
                            //2) less window arrived, still need to wait
                            //3) all window arrived
                            if (intermediateWindow.getWindowWaitCounter() == 0) {
                                //process and send window
                                mergeWindow(intermediateTasks.get(window.queryId), intermediateWindow.window, window);
                                newWindowCollection.windowList.add(intermediateWindow.window);
                                //update task
                                iter.remove();
                                intermediateTasks.get(window.queryId).windowCounterAdd();
                            }
                            isNewWindow = false;
                            break;
                        }
                    }
                    //1) empty, 6)disorder window, keep it, create a new intermediate window
                    if (isNewWindow) {
                        IntermediateWindow intermediateWindow = new IntermediateWindow();
                        intermediateWindow.setWindowId(window.windowId);
                        intermediateWindow.setProcessTime(timeTemp);
                        intermediateWindow.setWindowWaitCounter(conf.localNumber / conf.intermediaNumber - 1);
                        intermediateWindow.window = window;
                        intermediateTasks.get(window.queryId).intermediateWindows.add(intermediateWindow);
                    }
                }
            }
            //centralized median & quantile & countbased
            else{
                if (intermediateTasks.get(window.queryId).query.getWindowType() != conf.COUNTBASED) {
                    //window expired
                    if(intermediateTasks.get(window.queryId).intermediateWindows.getFirst().getWindowId() > window.windowId){
                        window.windowId = intermediateTasks.get(window.queryId).intermediateWindows.getFirst().getWindowId();
                        windowFlag[1] = true;
                    //window end
                    }else if(intermediateTasks.get(window.queryId).intermediateWindows.getFirst().getWindowId() < window.windowId){
                        windowFlag[0] = true;
                        intermediateTasks.get(window.queryId).intermediateWindows.getFirst().setWindowId(window.windowId);
                    }
                    windowListForCen.add(window);
                }
            }
        });

        //no expired
        if(!windowFlag[1]){
            tupleBatchCounter++;
            tupleListForCen.addAll(windowCollection.tuples);
//            tupleListForCen.sort((a, b) -> Double.compare(a.DATA, b.DATA));
            //window end
            if(windowFlag[0] || tupleBatchCounter >= conf.transferBatchSize) {
                //sort
                tupleListForCen.sort((a, b) -> Double.compare(a.DATA, b.DATA));
                newWindowCollection.windowList.addAll(windowListForCen);
                newWindowCollection.tuples.addAll(tupleListForCen);
                tupleListForCen = new ArrayList<>();
                tupleBatchCounter = 0;
            }
        }
        //send reuslt
        if(!newWindowCollection.windowList.isEmpty())
            resultQueue.add(newWindowCollection);
    }

    void mergeWindow(IntermediateTask task, Window window, Window newWindow){
        if(task.query.getScenario() == conf.DeCentralizedAggregation) {
            switch (task.query.getFunction()) {
                case Configuration.COUNT: {
                    window.count+= newWindow.count;
                    break;
                }
                case Configuration.SUM: {
                    window.result+= newWindow.result;
                    break;
                }
                case Configuration.AVERAGE: {
                    window.count+= newWindow.count;
                    window.result+= newWindow.result;
                    break;
                }
                case Configuration.MAX: {
                    window.result = Math.max(window.result, newWindow.result);
                    break;
                }
                case Configuration.MIN: {
                    window.result = Math.min(window.result, newWindow.result);
                    break;
                }
                default:
                    break;
            }
        }
    }

    private void queryPreProcess(){
        //get all queries, it will be skipped when all queries retrieved
        while(intermediateTasks.size() < conf.queryNumber){
            if(!queryQueue.isEmpty()){
                IntermediateTask task = new IntermediateTask();
                task.query = (Query) queryQueue.poll();

                task.setTaskId(task.query.getQueryId());
                task.setWindowCounter(1);
                task.intermediateWindows = new LinkedList<IntermediateWindow>();
                intermediateTasks.add(task);

                //for centralized aggregation, initialize median and quantile
                if(task.query.getScenario() == conf.CentralizedAggregation
                        && task.query.getWindowType() != conf.COUNTBASED){
                    IntermediateWindow intermediateWindow = new IntermediateWindow();
                    intermediateWindow.setWindowId(1);
                    task.intermediateWindows.add(intermediateWindow);
                }

            }else{
                try {
                    Thread.sleep(conf.queryWait);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        if(!queryQueue.isEmpty()){
            IntermediateTask task = new IntermediateTask();
            task.query = (Query) queryQueue.poll();
            task.setTaskId(task.query.getQueryId());
            task.setWindowCounter(1);
            task.intermediateWindows = new LinkedList<IntermediateWindow>();
            intermediateTasks.add(task);

            //for centralized aggregation, initialize median and quantile
            if(task.query.getScenario() == conf.CentralizedAggregation
                    && task.query.getWindowType() != conf.COUNTBASED){
                IntermediateWindow intermediateWindow = new IntermediateWindow();
                intermediateWindow.setWindowId(1);
                task.intermediateWindows.add(intermediateWindow);
            }
        }

    }

}
