package De.Hpi.Desis.IntermediaNode;

import De.Hpi.Desis.Configure.Configuration;
import De.Hpi.Desis.Dao.*;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;


public class IntermediateComputationEngine implements Runnable {

    private Configuration conf;
    private ConcurrentLinkedQueue<WindowCollection> resultQueueFromLocal;
    private ConcurrentLinkedQueue<WindowCollection> resultQueue;
    private ConcurrentLinkedQueue<Query> queryQueue;
    private ArrayList<IntermediateTask> intermediateTasks;

     IntermediateComputationEngine(ConcurrentLinkedQueue<WindowCollection> resultQueue, ConcurrentLinkedQueue<WindowCollection> resultQueueFromLocal,
                                   ConcurrentLinkedQueue<Query> queryQueue, Configuration conf){
         this.conf = conf;
         this.resultQueueFromLocal =resultQueueFromLocal;
         this.resultQueue = resultQueue;
         this.queryQueue = queryQueue;
         this.intermediateTasks = new LinkedList<IntermediateTask>();
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

        windowCollection.windowList.forEach(window -> {

            //clean the expired intermediate windows, and find the aim one
            IntermediateWindow intermediateWindow = null;
            Iterator<IntermediateWindow> iter = intermediateTasks.get(window.queryId).intermediateWindowLinkedList.iterator();

            while (iter.hasNext()) {
                IntermediateWindow intermediateWindowItr = iter.next();
                //4)expired, process and send them all
                if(timeTemp - intermediateWindowItr.getProcessTime() > conf.EXPIREDTIME ) {
                    //send it
                    newWindowCollection.windowList.add(intermediateWindowItr.window);
                    intermediateTasks.get(window.queryId).windowCounterAdd();
                    iter.remove();
                    //5) old window arrived throw, or find aim intermediate window
                }else if(intermediateTasks.get(window.queryId).getWindowCounter() <= window.windowId
                        && intermediateWindowItr.getWindowId() == window.windowId){
                    //this is not first window
                        intermediateWindow = intermediateWindowItr;
                }
            }

            if(intermediateTasks.get(window.queryId).getWindowCounter() <= window.windowId) {
                //1) empty, 6)disorder window, keep it, create a new intermediate window
                if (intermediateWindow == null) {
                    intermediateWindow = new IntermediateWindow();
                    intermediateWindow.setWindowId(window.windowId);
                    intermediateWindow.setProcessTime(timeTemp);
                    intermediateWindow.setWindowWaitCounter(conf.localNumber / conf.intermediaNumber - 1);
                    intermediateWindow.window = window;
                    intermediateTasks.get(window.queryId).intermediateWindowLinkedList.add(intermediateWindow);
                } else {
                    //2) less window arrived, still need to wait
                    if (intermediateWindow.getWindowWaitCounter() > 1) {
                        intermediateWindow.deleteWindowWaittingCounter();
                        mergeWindow(task, intermediateWindow.window, window);
                        // 3) all window arrived
                    } else {
                        mergeWindow(task, intermediateWindow.window, window);
                        resultQueue.add(intermediateWindow.window);
                        task.windowCounterAdd();
                        task.intermediateWindowLinkedList.remove(intermediateWindow);
                    }
                }
            }

        });
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
        }else{
            //merge two list
            window.tuples.addAll(newWindow.tuples);
            newWindow.tuples.sort((a, b) -> Double.compare(a.DATA, b.DATA));
        }
//        window.tupleCounter+=newWindow.tupleCounter;
    }

    private void queryPreProcess(){
        //get all queries, it will be skipped when all queries retrieved
        while(intermediateTasks.size() < conf.queryNumber){
            if(!queryQueue.isEmpty()){
                IntermediateTask task = new IntermediateTask();
                task.query = (Query) queryQueue.poll();

                task.setTaskId(task.query.getQueryId());
                task.setWindowCounter(1);
                task.intermediateWindowLinkedList = new LinkedList<IntermediateWindow>();
                intermediateTasks.add(task);
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
            task.intermediateWindowLinkedList = new LinkedList<IntermediateWindow>();
            intermediateTasks.add(task);
        }

    }

}
