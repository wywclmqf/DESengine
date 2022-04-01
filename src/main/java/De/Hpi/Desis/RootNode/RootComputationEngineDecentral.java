package De.Hpi.Desis.RootNode;

import De.Hpi.Desis.Configure.Configuration;
import De.Hpi.Desis.Dao.*;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

public class RootComputationEngineDecentral implements Runnable {

    private Configuration conf;
    private ConcurrentLinkedQueue<WindowCollection> resultQueue;
    private ConcurrentLinkedQueue<WindowCollection> resultFromIntermedia;
    private ConcurrentLinkedQueue<Query> queryQueue;
    private ArrayList<RootTask> rootTasks;
    private long tupleCounter;

    RootComputationEngineDecentral(ConcurrentLinkedQueue<WindowCollection> resultFromIntermedia, Configuration conf,
                                   ConcurrentLinkedQueue<WindowCollection> resultQueue,
                                   ConcurrentLinkedQueue<Query> queryQueue){
        this.conf = conf;
        this.resultQueue =resultQueue;
        this.resultFromIntermedia =resultFromIntermedia;
        this.rootTasks = new ArrayList<>();
        this.queryQueue = queryQueue;
        this.tupleCounter = 0;
    }

    public void run() {
        while (true) {
            if (!resultFromIntermedia.isEmpty()) {
                //to read all queries
                queryPreProcess();
                //get intermediate result from local nodes
                WindowCollection windowCollection = resultFromIntermedia.poll();
                long timeTemp = System.currentTimeMillis();
                windowProcess(windowCollection, timeTemp);
            }
        }
    }

    //can not process duplicate windows
    private void windowProcess(WindowCollection windowCollection, long timeTemp){
        //local window
        //the all scenarios are,
        // 1)empty, 2)less window arrived, put it into list
        // 3) all window arrived 4)expired, process and send them all
        // 5) old window arrived throw
        // 6)disorder window, keep it

        //the windows that have same window ids should be in a same intermediate window
        //and the intermediate window is to collect same windowid windows that from different nodes.

        WindowCollection newWindowCollection = new WindowCollection();
        newWindowCollection.windowList = new ArrayList<>();
        newWindowCollection.tuples = new ArrayList<>();
        //record central windows
        ArrayList<Window> windowListForCen = new ArrayList<>();
        //if there is median or quantile window 1) end or 2) expired 3) process
        boolean[] windowFlag = {false, false, false};

        windowCollection.windowList.forEach(window -> {
            RootTask rootTask = rootTasks.get(window.queryId);
            //decentralized
            if(rootTask.query.getScenario() == conf.DeCentralizedAggregation) {
                //5) old window arrived throw, or find aim intermediate window
                if (rootTask.getWindowCounter() <= window.windowId) {
                    //if we need to build a new window
                    boolean isNewWindow = true;
                    //clean the expired intermediate windows, and find the aim one
                    Iterator<RootWindow> iter = rootTask.rootWindows.iterator();
                    while (iter.hasNext()) {
                        RootWindow rootWindow = iter.next();
                        //4)expired, process and send them all
                        if (timeTemp - rootWindow.getProcessTime() > conf.EXPIREDTIME) {
                            //send it
                            newWindowCollection.windowList.add(rootWindow.window);
                            //calculate final result
                            calculateWindow(rootTask, rootWindow.window);
                            //update task
                            iter.remove();
                            rootTask.windowCounterAdd();
                        } else if (rootWindow.getWindowId() == window.windowId) {
                            //we find aim intermediate window
                            //this is not first window
                            rootWindow.deleteWindowWaitingCounter();
                            mergeWindow(rootTask, rootWindow.window, window);
                            //2) less window arrived, still need to wait
                            //3) all window arrived
                            if (rootWindow.getWindowWaitCounter() == 0) {
                                //process and send window
                                mergeWindow(rootTask, rootWindow.window, window);
                                newWindowCollection.windowList.add(rootWindow.window);
                                //calculate final result
                                calculateWindow(rootTask, rootWindow.window);
                                //update task
                                iter.remove();
                                rootTask.windowCounterAdd();
                            }
                            isNewWindow = false;
                            break;
                        }
                    }
                    //1) empty, 6)disorder window, keep it, create a new intermediate window
                    if (isNewWindow) {
                        RootWindow rootWindow = new RootWindow();
                        rootWindow.setWindowId(window.windowId);
                        rootWindow.setProcessTime(timeTemp);
                        rootWindow.setWindowWaitCounter(conf.localNumber / conf.intermediaNumber - 1);
                        rootWindow.window = window;
                        rootTask.rootWindows.add(rootWindow);
                    }
                }
            }
            //centralized median & quantile & countbased
            else{
                //for timebased window
                if(rootTask.query.getWindowType() != conf.COUNTBASED){
                    //save the batch index
                    rootTask.batchList.add(windowCollection.tuples);
                    //window expired
                    if(rootTask.rootWindows.getFirst().getWindowId() > window.windowId){
                        window.windowId = rootTask.rootWindows.getFirst().getWindowId();
//                        windowFlag[1] = true;
                        //window end
                    }else if(rootTask.rootWindows.getFirst().getWindowId() < window.windowId){
//                        windowFlag[0] = true;
                        mergeWindow(rootTask, rootTask.rootWindows.get(0).window, window);
                        rootTask.rootWindows.get(0).setWindowId(window.windowId);
                        newWindowCollection.windowList.add(rootTask.rootWindows.get(0).window);
                    }
                }
                //for count based window
                else{
                    //window end nonDecomposable
                    if(rootTask.query.getFunction() == conf.MEDIAN || rootTask.query.getFunction() == conf.QUANTILE){
                        //window end
                        if(rootTask.rootWindows.get(0).window.count + windowCollection.tuples.size()
                                >= rootTask.query.getRange()){
                            //calculate
                            mergeWindowCountBased(rootTask, rootTask.rootWindows.get(0).window, windowCollection.tuples,
                                    0,(int) (rootTask.query.getRange() - rootTask.rootWindows.get(0).window.count)-1);
                            rootTask.rootWindows.get(0).window.count = rootTask.query.getRange();

                            //send window
                            newWindowCollection.windowList.add(rootTask.rootWindows.get(0).window);

                            //update new window
                            window.count = rootTask.rootWindows.get(0).window.count + windowCollection.tuples.size() - rootTask.query.getRange();


                            rootTask.batchList.add(new ArrayList(windowCollection.tuples.stream()
                                    .skip((int) window.count).limit(windowCollection.tuples.size() - 1).collect(Collectors.toList())));
                           rootTask.rootWindows.get(0).window = window;
                        }else{
                            rootTask.batchList.add(windowCollection.tuples);
                            rootTask.rootWindows.get(0).window.count += windowCollection.tuples.size();
                        }
                    }else{
                        //window end decomposable
                        if(rootTask.rootWindows.get(0).window.count + windowCollection.tuples.size()
                                >= rootTask.query.getRange()){
                            //calculate
                            mergeWindowCountBased(rootTask, rootTask.rootWindows.get(0).window, windowCollection.tuples,
                                    0,(int) (rootTask.query.getRange() - rootTask.rootWindows.get(0).window.count)-1);
                            rootTask.rootWindows.get(0).window.count = rootTask.query.getRange();
                            calculateWindow(rootTask, rootTask.rootWindows.get(0).window);
                            //send window
                            newWindowCollection.windowList.add(rootTask.rootWindows.get(0).window);

                            //update new window
                            window.count = rootTask.rootWindows.get(0).window.count + windowCollection.tuples.size() - rootTask.query.getRange();
                            mergeWindowCountBased(rootTask, window, windowCollection.tuples,
                                    (int) window.count, windowCollection.tuples.size() - 1);
                            rootTask.rootWindows.get(0).window = window;
                        }else {
                            mergeWindowCountBased(rootTask, rootTask.rootWindows.get(0).window, windowCollection.tuples,
                                    0,windowCollection.tuples.size() - 1);
                            rootTask.rootWindows.get(0).window.count += windowCollection.tuples.size();
                        }
                    }
                }
            }
        });

        if(!newWindowCollection.windowList.isEmpty())
            resultQueue.add(newWindowCollection);

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
            //centralized
            switch (task.query.getFunction()) {
                case Configuration.MEDIAN: {
                    if(!task.batchList.isEmpty()){
                        ArrayList<Tuple> tuples = new ArrayList<>();
                        task.batchList.forEach(batch -> {
                            tuples.addAll(batch);
                        });
                        //sort
                        tuples.sort((a, b) -> Double.compare(a.DATA, b.DATA));
                        window.result = tuples.get((tuples.size() - 1) / 2).DATA;;
                        task.batchList = new ArrayList<>();
                    }else
                        window.result = 0;
                    break;
                }
                case Configuration.QUANTILE: {
                    if(!task.batchList.isEmpty()){
                        ArrayList<Tuple> tuples = new ArrayList<>();
                        task.batchList.forEach(batch -> {
                            tuples.addAll(batch);
                        });
                        //sort
                        tuples.sort((a, b) -> Double.compare(a.DATA, b.DATA));
                        window.result = tuples.get((tuples.size() - 1) / 4).DATA;;
                        task.batchList = new ArrayList<>();
                    }else
                        window.result = 0;
                    break;
                }
                default:
                    break;
            }
        }
    }

    void mergeWindowCountBased(RootTask task, Window window, ArrayList<Tuple> tuples,int first, int end){
        switch (task.query.getFunction()) {
            case Configuration.SUM: {
                for(int i = first; i < end; i++){
                    window.result += tuples.get(i).DATA;
                }
                break;
            }
            case Configuration.AVERAGE: {
//                window.result += tuples.stream().mapToDouble(item -> item.DATA).sum();
                for(int i = first; i < end; i++){
                    window.result += tuples.get(i).DATA;
                }
                break;
            }
            case Configuration.MAX: {
//                window.result = Math.max(window.result, tuples.stream().mapToDouble(item -> item.DATA).max().getAsDouble());
                for(int i = first; i < end; i++){
                    window.result = Math.max(window.result, tuples.get(i).DATA);
                }
                break;
            }
            case Configuration.MIN: {
//                window.result = Math.min(window.result, tuples.stream().mapToDouble(item -> item.DATA).min().getAsDouble());
                for(int i = first; i < end; i++){
                    window.result = Math.min(window.result, tuples.get(i).DATA);
                }
                break;
            }
            case Configuration.MEDIAN: {
                task.batchList.add(new ArrayList(tuples.stream().skip(first).limit(end).collect(Collectors.toList())));
                ArrayList<Tuple> tuplesTemp = new ArrayList<>();
                task.batchList.forEach(batch -> {
                    tuplesTemp.addAll(batch);
                });
                //sort
                tuplesTemp.sort((a, b) -> Double.compare(a.DATA, b.DATA));
                window.result = tuplesTemp.get((tuplesTemp.size() - 1) / 2).DATA;;
                task.batchList = new ArrayList<>();
                break;
            }
            case Configuration.QUANTILE: {
                task.batchList.add(new ArrayList(tuples.stream().skip(first).limit(end).collect(Collectors.toList())));
                ArrayList<Tuple> tuplesTemp = new ArrayList<>();
                task.batchList.forEach(batch -> {
                    tuplesTemp.addAll(batch);
                });
                //sort
                tuplesTemp.sort((a, b) -> Double.compare(a.DATA, b.DATA));
                window.result = tuplesTemp.get((tuplesTemp.size() - 1) / 4).DATA;;
                task.batchList = new ArrayList<>();
                break;
            }
            default:
                break;
        }

    }

    private void calculateWindow(RootTask task, Window window){
        switch (task.query.getFunction()) {
            case Configuration.COUNT: {
                window.result = window.count;
                break;
            }
            case Configuration.SUM: {
                window.result = window.result;
                break;
            }
            case Configuration.AVERAGE: {
                window.result = window.result / window.count;
                break;
            }
//            case Configuration.MAX:
//            case Configuration.MIN:
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
                task.setWindowCounter(1);
                task.rootWindows = new LinkedList<RootWindow>();
                rootTasks.add(task);
                //for centralized aggregation, initialize median and quantile
                if(task.query.getScenario() == conf.CentralizedAggregation) {
                    RootWindow rootWindow = new RootWindow();
                    rootWindow.window = new Window();
                    rootWindow.window.queryId = task.query.getQueryId();
                    rootWindow.setWindowId(1);
                    task.batchList = new ArrayList<ArrayList<Tuple>>();
                    task.rootWindows.add(rootWindow);
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
            RootTask task = new RootTask();
            task.query = (Query) queryQueue.poll();
            task.setTaskId(task.query.getQueryId());
            task.setWindowCounter(1);
            task.rootWindows = new LinkedList<RootWindow>();
            rootTasks.add(task);
            //for centralized aggregation, initialize median and quantile
            if(task.query.getScenario() == conf.CentralizedAggregation) {
                RootWindow rootWindow = new RootWindow();
                rootWindow.window = new Window();
                rootWindow.setWindowId(1);
                task.batchList = new ArrayList<ArrayList<Tuple>>();
                task.rootWindows.add(rootWindow);
            }
        }

    }


}
