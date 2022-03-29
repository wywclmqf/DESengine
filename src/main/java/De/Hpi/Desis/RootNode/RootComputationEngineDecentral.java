package De.Hpi.Desis.RootNode;

import De.Hpi.Desis.Configure.Configuration;
import De.Hpi.Desis.Dao.Query;
import De.Hpi.Desis.Dao.RootTask;
import De.Hpi.Desis.Dao.RootWindow;
import De.Hpi.Desis.Dao.Window;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RootComputationEngineDecentral implements Runnable {

    private Configuration conf;
    private ConcurrentLinkedQueue<Window> resultQueue;
    private ConcurrentLinkedQueue<Window> resultFromIntermedia;
    private ConcurrentLinkedQueue<Query> queryQueue;
    private LinkedList<RootTask> rootTasks;

    RootComputationEngineDecentral(ConcurrentLinkedQueue<Window> resultFromIntermedia, Configuration conf,
                                   ConcurrentLinkedQueue<Window> resultQueue,
                                   ConcurrentLinkedQueue<Query> queryQueue){
        this.conf = conf;
        this.resultQueue =resultQueue;
        this.resultFromIntermedia =resultFromIntermedia;
        this.rootTasks = new LinkedList<RootTask>();
        this.queryQueue = queryQueue;
    }

    public void run() {
        while (true) {
            if (!resultFromIntermedia.isEmpty()) {
                //to read all queries
                queryPreProcess();
                //get intermediate result from local nodes
                Window window = resultFromIntermedia.poll();
                long timeTemp = System.currentTimeMillis();
                windowProcess(window, timeTemp);
            }
        }
    }

    //can not process duplicate windows
    private void windowProcess(Window window, long timeTemp){
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
//        rootTasks.forEach(task -> {
//            //clean the expired intermediate windows, and find the aim one
//            if(task.query.getScenario() == conf.DeCentralizedAggregation) {
//                RootWindow rootWindow = null;
//                Iterator<RootWindow> iter = task.rootWindowLinkedList.iterator();
//                while (iter.hasNext()) {
//                    RootWindow rootWindowIter = iter.next();
//                    //4)expired, process and send them all
//                    if (timeTemp - rootWindowIter.getProcessTime() > conf.EXPIREDTIME) {
//                        //send it
//                        calculateWindow(task, rootWindowIter.window);
//                        resultQueue.add(rootWindowIter.window);
//                        task.windowCounterAdd();
//                        iter.remove();
//                        //5) old window arrived trhow, or find aim intermediate window
//                    } else if (task.getWindowCounter() < window.getWindowId() && task.getTaskId() == window.getQueryId()) {
//                        //this is not first window
//                        if (rootWindowIter.getWindowId() == window.getWindowId()) {
//                            rootWindow = rootWindowIter;
//                        }
//                    }
//                }
//                if (task.getWindowCounter() < window.getWindowId()
//                        && task.getTaskId() == window.getQueryId()) {
//                    //1) empty, 6)disorder window, keep it, create a new intermediate window
//                    if (rootWindow == null) {
//                        rootWindow = new RootWindow();
//                        rootWindow.setWindowId(window.getWindowId());
//                        rootWindow.setProcessTime(timeTemp);
//                        rootWindow.setWindowWaittingCounter(conf.intermediaNumber / conf.rootNumber - 1);
//                        rootWindow.window = window;
//                        task.rootWindowLinkedList.add(rootWindow);
//                    } else {
//                        //2) less window arrived, still need to wait
//                        if (rootWindow.getWindowWaittingCounter() > 1) {
//                            rootWindow.deleteWindowWaittingCounter();
//                            mergeWindow(task, rootWindow.window, window);
//                            // 3) all window arrived
//                        } else {
//                            mergeWindow(task, rootWindow.window, window);
//                            calculateWindow(task, rootWindow.window);
//                            resultQueue.add(rootWindow.window);
//                            task.windowCounterAdd();
//                            task.rootWindowLinkedList.remove(rootWindow);
//                        }
//                    }
//                }
//            }
//        });
    }

    void mergeWindow(RootTask task, Window window, Window newWindow){
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

    private void calculateWindow(RootTask task, Window window){
        switch (task.query.getFunction()) {
            case Configuration.COUNT: {
                window.result = window.count;
                break;
            }
//            case Configuration.SUM:
            case Configuration.AVERAGE: {
                window.result = window.result / window.count;
                break;
            }
//            case Configuration.MAX:
//            case Configuration.MIN:
            case Configuration.QUANTILE: {
                int index = window.tuples.size() / 4;
                window.result = window.tuples.get(index).DATA;
                break;
            }
            case Configuration.MEDIAN: {
                int index = window.tuples.size() / 2;
                window.result = window.tuples.get(index).DATA;
                break;
            }
            default:
                break;
        }
    }

    private void queryPreProcess(){
        //get all queries, it will be skipped when all queries retrieved
        while(rootTasks.size() < conf.queryNumber){
            if(!queryQueue.isEmpty()){
                RootTask task = new RootTask();
                task.query = (Query) queryQueue.poll();
                task.setTaskId(task.query.getQueryId());
                task.rootWindowLinkedList = new LinkedList<RootWindow>();
                rootTasks.add(task);
            }else{
                try {
                    Thread.sleep(conf.queryWait);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        if(!queryQueue.isEmpty()){
            RootTask task = new RootTask();
            task.query = (Query) queryQueue.poll();
            task.setTaskId(task.query.getQueryId());
            task.rootWindowLinkedList = new LinkedList<RootWindow>();
            rootTasks.add(task);
        }

    }


}
