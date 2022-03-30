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
    private LinkedList<IntermediateTask> intermediateTasks;

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
    private void windowProcess(WindowCollection window, long timeTemp){
//        //local window
//        //the all scenarios are,
//        // 1)empty, 2)less window arrived, put it into list
//        // 3) all window arrived 4)expired, process and send them all
//        // 5) old window arrived trhow
//        // 6)disorder window, keep it
//
//        //the windows that have same window ids should be in a same intermediate window
//        //and the intermediate window is to collect same windowid windows that from different nodes.
//
        intermediateTasks.forEach(task -> {
            //clean the expired intermediate windows, and find the aim one
            IntermediateWindow intermediateWindow = null;
            Iterator<IntermediateWindow> iter = task.intermediateWindowLinkedList.iterator();
            while (iter.hasNext()) {
                IntermediateWindow intermediateWindowItr = iter.next();
                //4)expired, process and send them all
                if(timeTemp - intermediateWindowItr.getProcessTime() > conf.EXPIREDTIME ) {
                    //send it
                    resultQueue.add(intermediateWindowItr.window);
                    task.windowCounterAdd();
                    iter.remove();
                //5) old window arrived throw, or find aim intermediate window
                }else if(task.getWindowCounter() < window.getWindowId()
                        && task.getTaskId() == window.getQueryId()){
                    //this is not first window
                    if(intermediateWindowItr.getWindowId() == window.getWindowId()){
                        intermediateWindow = intermediateWindowItr;
                    }
                }
            }
            if(task.getWindowCounter() < window.getWindowId()
                    && task.getTaskId() == window.getQueryId()) {
                //1) empty, 6)disorder window, keep it, create a new intermediate window
                if (intermediateWindow == null) {
                    intermediateWindow = new IntermediateWindow();
                    intermediateWindow.setWindowId(window.getWindowId());
                    intermediateWindow.setProcessTime(timeTemp);
                    intermediateWindow.setWindowWaittingCounter(conf.localNumber / conf.intermediaNumber - 1);
                    intermediateWindow.window = window;
                    task.intermediateWindowLinkedList.add(intermediateWindow);
                } else {
                    //2) less window arrived, still need to wait
                    if (intermediateWindow.getWindowWaittingCounter() > 1) {
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
