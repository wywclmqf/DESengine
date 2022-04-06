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
    private LinkedList<TupleBatch> tupleBatches;
    private int currentSliceId;
    private long tupleCounter;
    private boolean countBasedFlag;

    RootComputationEngineDecentral(ConcurrentLinkedQueue<WindowCollection> resultFromIntermedia, Configuration conf,
                                   ConcurrentLinkedQueue<WindowCollection> resultQueue,
                                   ConcurrentLinkedQueue<Query> queryQueue){
        this.conf = conf;
        this.resultQueue =resultQueue;
        this.resultFromIntermedia =resultFromIntermedia;
        this.rootTasks = new ArrayList<>();
        this.tupleBatches = new LinkedList<>();
        this.queryQueue = queryQueue;
        this.currentSliceId = 1;
        this.tupleCounter = 0;
        this.countBasedFlag = false;
    }

    public void run() {
        queryPreProcess();
        while (true) {
            if (!resultFromIntermedia.isEmpty()) {
                //to read all queries
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
        //windowCounter 1. >wc save 2. <wc drop 3. =wc process
        //drop < windowcounter
        WindowCollection newWindowCollection = new WindowCollection();
        newWindowCollection.windowList = new ArrayList<>();
        newWindowCollection.tuples = new ArrayList<>();

        boolean newTupleBatch = false;
        //save tuple batch, if new id less than old id, these are disorder data just drop
        if(currentSliceId <= windowCollection.sliceId) {
            TupleBatch tupleBatch = new TupleBatch();
            tupleBatch.sliceId = windowCollection.sliceId;
            tupleBatch.sliceCounter = windowCollection.sliceCounter;
            tupleBatch.tuples = windowCollection.tuples;
            tupleCounter += windowCollection.tuples.size();
            tupleBatches.addFirst(tupleBatch);
            //can process countbased window
            newTupleBatch = true;
            if(currentSliceId < windowCollection.sliceId){
                currentSliceId = windowCollection.sliceId;
            }
        }

        windowCollection.windowList.forEach(window -> {
            RootTask task = rootTasks.get(window.queryId);
            //time basedwindow
            if(task.query.getWindowType() != conf.COUNTBASED) {
                //5) old window arrived throw, or find aim intermediate window
                if (task.getWindowCounter() <= window.windowId) {
                    //process median or quantile
//                    if(task.query.getScenario() == conf.CentralizedAggregation){
//                        task.batchList.add(windowCollection.tuples);
//                    }
                    //if there is only child node
                    if(conf.localNumber / conf.intermediaNumber - 1 == 0){
                        //process and send window
//                        newWindowCollection.windowList.add(window);
                        //calculate final result and send it
                        calculateWindow(task, window, newWindowCollection);
                        //update task
                        task.windowCounterAdd();
                        return;
                    }
                    //if we need to build a new window
                    boolean isNewWindow = true;
                    //clean the expired intermediate windows, and find the aim one
                    Iterator<RootWindow> iter = task.rootWindows.iterator();
                    while (iter.hasNext()) {
                        RootWindow rootWindow = iter.next();
                        //4)expired, process and send them all
                        if (timeTemp - rootWindow.getProcessTime() > conf.EXPIREDTIME) {
                            //send it
//                            newWindowCollection.windowList.add(rootWindow.window);
                            //calculate final result and send it
                            calculateWindow(task, rootWindow.window, newWindowCollection);
                            //update task
                            iter.remove();
                            task.windowCounterAdd();
                        } else if (rootWindow.getWindowId() == window.windowId) {
                            //we find aim intermediate window
                            //this is not first window
                            rootWindow.deleteWindowWaitingCounter();
                            mergeWindow(task, rootWindow.window, window);
                            //2) less window arrived, still need to wait
                            //3) all window arrived
                            if (rootWindow.getWindowWaitCounter() == 0) {
                                //process and send window
//                            newWindowCollection.windowList.add(rootWindow.window);
                                //calculate final result and send it
                                calculateWindow(task, rootWindow.window, newWindowCollection);
                                //update task
                                iter.remove();
                                task.windowCounterAdd();
                                //process median and quantile
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
                        task.rootWindows.add(rootWindow);
                    }
                }
            }
        });

        //countbased window, the windowlist of windowCollcetion is empty
        if(countBasedFlag && newTupleBatch){
            rootTasks.forEach(task -> {
                //for count based window
                //window end nonDecomposable
                if(task.query.getFunction() == conf.MEDIAN || task.query.getFunction() == conf.QUANTILE){
                    //window end
                    Window window = task.rootWindows.get(0).window;
                    if(tupleCounter - window.count
                            >= task.query.getRange()){
                        //calculate
                        mergeWindowCountBased(task, task.rootWindows.get(0).window, windowCollection.tuples,
                                0,(int) (task.query.getRange() - task.rootWindows.get(0).window.count)-1);
                        //calculate final result and send it
                        calculateWindow(task, window, newWindowCollection);

                        //update new window
                        task.windowCounterAdd();
                        window.windowId = task.getWindowCounter();
                        window.result = 0;
                        window.count += task.query.getRange();
                    }else{
                        //calculate
                        mergeWindowCountBased(task, task.rootWindows.get(0).window, windowCollection.tuples,
                                0,(int) (task.query.getRange() - task.rootWindows.get(0).window.count)-1);
                    }
                }else{
                    //window end decomposable
                    if(task.rootWindows.get(0).window.count + windowCollection.tuples.size()
                            >= task.query.getRange()){
                        //calculate
                        mergeWindowCountBased(task, task.rootWindows.get(0).window, windowCollection.tuples,
                                0,(int) (task.query.getRange() - task.rootWindows.get(0).window.count)-1);
                        task.rootWindows.get(0).window.count = task.query.getRange();
//                        calculateWindow(task, task.rootWindows.get(0).window);
                        //send window
                        newWindowCollection.windowList.add(task.rootWindows.get(0).window);

                        //update new window
                        window.count = task.rootWindows.get(0).window.count + windowCollection.tuples.size() - task.query.getRange();
                        mergeWindowCountBased(task, window, windowCollection.tuples,
                                (int) window.count, windowCollection.tuples.size() - 1);
                        task.rootWindows.get(0).window = window;
                    }else {
                        mergeWindowCountBased(task, task.rootWindows.get(0).window, windowCollection.tuples,
                                0,windowCollection.tuples.size() - 1);
                        task.rootWindows.get(0).window.count += windowCollection.tuples.size();
                    }
                }
            });
        }





        if(!newWindowCollection.windowList.isEmpty()) {
            resultQueue.add(newWindowCollection);
        }

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
        }
    }

    private void calculateWindow(RootTask task, Window window, WindowCollection windowCollection){
        switch (task.query.getFunction()) {
            case Configuration.COUNT: {
                window.result = window.count;
                task.querySubs.forEach(querySub -> {
                    Window tempWindow = new Window();
                    tempWindow.queryId = querySub.queryId;
                    tempWindow.windowId = window.windowId;
                    tempWindow.result = window.result;
                    windowCollection.windowList.add(tempWindow);
                });
                break;
            }
            case Configuration.SUM: {
                window.result = window.result;
                task.querySubs.forEach(querySub -> {
                    Window tempWindow = new Window();
                    tempWindow.queryId = querySub.queryId;
                    tempWindow.windowId = window.windowId;
                    tempWindow.result = window.result;
                    windowCollection.windowList.add(tempWindow);
                });
                break;
            }
            case Configuration.AVERAGE: {
                window.result = window.result / window.count;
                task.querySubs.forEach(querySub -> {
                    Window tempWindow = new Window();
                    tempWindow.queryId = querySub.queryId;
                    tempWindow.windowId = window.windowId;
                    tempWindow.result = window.result;
                    windowCollection.windowList.add(tempWindow);
                });
                break;
            }
            case Configuration.MEDIAN: {
                ArrayList<Tuple> tuplesTemp = new ArrayList<>();
//                long temp = tupleBatches.stream()
//                        .filter(tupleBatch -> tupleBatch.sliceId > window.sliceId - window.sliceCounter).
//                        .map(tupleBatch -> tupleBatch.tuples).count();
                tupleBatches.forEach(tupleBatch -> {
                    if(tupleBatch.sliceId > window.sliceId - window.sliceCounter && tupleBatch.sliceId <= window.sliceId){
                        tupleBatch.sliceCounter--;
                        tuplesTemp.addAll(tupleBatch.tuples);
                    }
                });
                tupleBatches.removeIf(tupleBatch -> tupleBatch.sliceCounter <= 0);
                tuplesTemp.sort((a, b) -> Double.compare(a.DATA, b.DATA));
                task.querySubs.forEach(querySub -> {
                    Window tempWindow = new Window();
                    tempWindow.queryId = querySub.queryId;
                    tempWindow.windowId = window.windowId;
                    tempWindow.result = tuplesTemp.get((tuplesTemp.size() - 1) / 2).DATA;
                    windowCollection.windowList.add(tempWindow);
                });
                break;
            }
            case Configuration.QUANTILE: {
                ArrayList<Tuple> tuplesTemp = new ArrayList<>();
                tupleBatches.forEach(tupleBatch -> {
                    if(tupleBatch.sliceId > window.sliceId - window.sliceCounter && tupleBatch.sliceId <= window.sliceId){
                        tupleBatch.sliceCounter--;
                        tuplesTemp.addAll(tupleBatch.tuples);
                    }
                });
                tupleBatches.removeIf(tupleBatch -> tupleBatch.sliceCounter <= 0);
                tuplesTemp.sort((a, b) -> Double.compare(a.DATA, b.DATA));
                task.querySubs.forEach(querySub -> {
                    Window tempWindow = new Window();
                    tempWindow.queryId = querySub.queryId;
                    tempWindow.windowId = window.windowId;
                    tempWindow.result = tuplesTemp.get((int) ((tuplesTemp.size() - 1)  * querySub.functionAddition)).DATA;
                    windowCollection.windowList.add(tempWindow);
                });
                break;
            }
            case Configuration.MAX:{
                task.querySubs.forEach(querySub -> {
                    Window tempWindow = new Window();
                    tempWindow.queryId = querySub.queryId;
                    tempWindow.windowId = window.windowId;
                    tempWindow.result = window.result;
                    windowCollection.windowList.add(tempWindow);
                });
            }
            case Configuration.MIN:{
                task.querySubs.forEach(querySub -> {
                    Window tempWindow = new Window();
                    tempWindow.queryId = querySub.queryId;
                    tempWindow.windowId = window.windowId;
                    tempWindow.result = window.result;
                    windowCollection.windowList.add(tempWindow);
                });
            }
            default:
                break;
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
//            case Configuration.MEDIAN: {
//                task.batchList.add(new ArrayList(tuples.stream().skip(first).limit(end).collect(Collectors.toList())));
//                ArrayList<Tuple> tuplesTemp = new ArrayList<>();
//                task.batchList.forEach(batch -> {
//                    tuplesTemp.addAll(batch);
//                });
//                //sort
//                tuplesTemp.sort((a, b) -> Double.compare(a.DATA, b.DATA));
//                window.result = tuplesTemp.get((tuplesTemp.size() - 1) / 2).DATA;;
//                task.batchList = new ArrayList<>();
//                break;
//            }
//            case Configuration.QUANTILE: {
//                task.batchList.add(new ArrayList(tuples.stream().skip(first).limit(end).collect(Collectors.toList())));
//                ArrayList<Tuple> tuplesTemp = new ArrayList<>();
//                task.batchList.forEach(batch -> {
//                    tuplesTemp.addAll(batch);
//                });
//                //sort
//                tuplesTemp.sort((a, b) -> Double.compare(a.DATA, b.DATA));
//                window.result = tuplesTemp.get((tuplesTemp.size() - 1) / 4).DATA;;
//                task.batchList = new ArrayList<>();
//                break;
//            }
            default:
                break;
        }

    }

    private void queryPreProcess(){
        //get all queries, it will be skipped when all queries retrieved
        int queryNumber = conf.queryNumber;
        while(rootTasks.size() < queryNumber){
            if(!queryQueue.isEmpty()){
                Query query = (Query) queryQueue.poll();
                //merge same queries
                RootTask tempRootTask = rootTasks.stream()
                        .filter(rootTask -> rootTask.query.getEntireQuery().equalsIgnoreCase(query.getEntireQuery()))
                        .findFirst()
                        .orElse(null);
                if(tempRootTask != null){
                    QuerySub querySub = new QuerySub();
                    querySub.queryId = query.getQueryId();
                    querySub.functionAddition = query.getFunctionAddition();
                    tempRootTask.querySubs.add(querySub);
                    //end the loop
                    queryNumber--;
                    continue;
                }
                RootTask task = new RootTask();
                task.query = query;
                task.setTaskId(task.query.getQueryId());
                task.setWindowCounter(1);
                task.querySubs = new ArrayList<>();
                QuerySub querySub = new QuerySub();
                querySub.queryId = query.getQueryId();
                querySub.functionAddition = query.getFunctionAddition();
                task.querySubs.add(querySub);
                task.rootWindows = new LinkedList<RootWindow>();
                rootTasks.add(task);

                //countbasedflag and initialized count task
                if(query.getScenario() == conf.COUNTBASED){
                    RootWindow rootWindow = new RootWindow();
                    rootWindow.window = new Window();
                    rootWindow.setWindowId(1);
                    task.rootWindows.add(rootWindow);
                    countBasedFlag = true;
                }

            }else{
                try {
                    Thread.sleep(conf.queryWait);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
//        if(!queryQueue.isEmpty()){
//            RootTask task = new RootTask();
//            task.query = (Query) queryQueue.poll();
//            task.setTaskId(task.query.getQueryId());
//            task.setWindowCounter(1);
//            task.rootWindows = new LinkedList<RootWindow>();
//            rootTasks.add(task);
//            //for centralized aggregation, initialize median and quantile
//            if(task.query.getScenario() == conf.CentralizedAggregation) {
//                RootWindow rootWindow = new RootWindow();
//                rootWindow.window = new Window();
//                rootWindow.setWindowId(1);
//                task.rootWindows.add(rootWindow);
//            }
//        }
    }


}
