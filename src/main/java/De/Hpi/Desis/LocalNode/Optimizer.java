package De.Hpi.Desis.LocalNode;

import De.Hpi.Desis.Configure.Configuration;
import De.Hpi.Desis.Dao.*;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/*
1. windows that have gaps only increment window counter.

 */

public class Optimizer implements Runnable{

    private Configuration conf;
    private LinkedList<LocalTask> localTasks;
    private LinkedList<LocalWindow> localWindows;
    private ArrayList<Tuple> tupleList;
    private ConcurrentLinkedQueue<WindowCollection> intermediateResultQueue;
    private ConcurrentLinkedQueue<Query> queryQueue;
    private ConcurrentLinkedQueue<ArrayList<Tuple>> dataQueue;
    private long localWindowCounter;
    private long tupleCounter;

    private boolean nonDecomposable;
    private boolean hasCountBased;

    public Optimizer(Configuration conf, ConcurrentLinkedQueue<WindowCollection> intermediateResultQueue,
                                       ConcurrentLinkedQueue<Query> queryQueue , ConcurrentLinkedQueue<ArrayList<Tuple>> dataQueue) {
        this.conf = conf;
        this.intermediateResultQueue =intermediateResultQueue;
        this.queryQueue =queryQueue;
        this.dataQueue =dataQueue;
        this.localTasks = new LinkedList<>();
        this.localWindows = new LinkedList<>();
        this.tupleList = new ArrayList<>(conf.centralizedBatchSize);
        this.localWindowCounter = 0;
        this.tupleCounter = 0;
        this.nonDecomposable = false;
        this.hasCountBased = false;
    }

    public void run() {
        while(true) {
            //to read all queries
            queryPreProcess();

            if (!dataQueue.isEmpty()){
                ArrayList<Tuple> dataBuffer = dataQueue.poll();

                dataBuffer.forEach(tuple -> {
                    //get data
                    long timeTemp = System.currentTimeMillis();
                    //slice window
                    LocalisEventHere localisEventHere = isEventHere(tuple, timeTemp);

                    if(localisEventHere.isFinishWindow()){
                        endWindow(localisEventHere);
                    }
                    if(localisEventHere.isCreateNewWindow()){
                        createWindow(localisEventHere);
                    }
                    if(localisEventHere.isProcessWindow()){
                        //calculate the result the calculate action
                        processWindow(localisEventHere, tuple);
                    }
                });

            }

        }
    }

    //the end tuple of a window always belongs to next window,
    private LocalisEventHere isEventHere(Tuple tuple, long timeTemp){
        tupleCounterIncrement();
        //to record the window state of each query
        LocalisEventHere localisEventHere = new LocalisEventHere();
        //to break down functions into operators
        localisEventHere.functions = new boolean[conf.FUNCTIONS];
        //the window of this query is end and need to be calculated
        localisEventHere.endList = new boolean[localTasks.size()];
        //the window of this query is processing
        localisEventHere.processList = new int[localTasks.size()];
        //mark down the current state of window that belong to this query
//        localisEventHere.stateList = new int[localTasks.size()];
        //in case there is a long gap and multiple windows end
        localisEventHere.multipleWindowEndList = new int[localTasks.size()];

        localTasks.forEach(task -> {
            //iterate all the query and process the bound of query
            // decentralized aggregation
//            if(task.query.getScenario() == conf.DeCentralizedAggregation) {
            switch (task.query.getWindowType()) {
                //1------tumbling window
                case Configuration.TUMBING: {
                    if (task.getInitialTime() == 0) {
                        //for the state
                        task.windowSliceAdd();
                        localisEventHere.setCreateNewWindow(true);
                        localisEventHere.processList[task.getTaskId()] = task.getwindowSlices();
                        localisEventHere.addProcessCount(task.getwindowSlices());
                        //initial tumbing window
                        task.setProcessTime(timeTemp);
                        task.setInitialTime(timeTemp);
                    } else {
                        if ((timeTemp - task.getProcessTime()) > task.query.getRange()) {
                            localisEventHere.endList[task.getTaskId()] = true;
                            localisEventHere.setCreateNewWindow(true);
                            localisEventHere.setFinishWindow(true);
                            localisEventHere.processList[task.getTaskId()] = task.getwindowSlices();
                            localisEventHere.addProcessCount(task.getwindowSlices());
                            task.setWindowCount((int)(timeTemp - task.getInitialTime()) / task.query.getRange());
//                            localisEventHere.multipleWindowEndList[task.getTaskId()] =
//                                    (int) (timeTemp - task.getInitialTime()) / task.query.getRange();
                            //in case there is a super long gap
                            task.setProcessTime(task.getInitialTime() + task.getWindowCount()*task.query.getRange());
                            //task.setProcessTime(timeTemp - (timeTemp - task.getProcessTime()) % task.query.getRange());
                        }
                    }
                    localisEventHere.setProcessWindow(true);
                    localisEventHere.functions[task.query.getFunction()] = true;
                    break;
                }
                //2-----sliding window
                case Configuration.SLIDING: {
                    //initial sliding window
                    //event time to start window, processing time to end window
                    if (task.getInitialTime() == 0) {
                        //for the state
                        task.windowSliceAdd();
                        localisEventHere.setCreateNewWindow(true);
                        localisEventHere.processList[task.getTaskId()] = task.getwindowSlices();
                        localisEventHere.addProcessCount(task.getwindowSlices());
                        task.setProcessTime(timeTemp);
                        task.setEventTime(timeTemp);
                        task.setInitialTime(timeTemp);
                    } else {
                        //window end
                        if ((timeTemp - task.getProcessTime()) > task.query.getRange()) {
                            //for the state
                            task.windowSliceDelete();
                            localisEventHere.endList[task.getTaskId()] = true;
                            localisEventHere.setCreateNewWindow(true);
                            localisEventHere.setFinishWindow(true);
                            localisEventHere.processList[task.getTaskId()] = task.getwindowSlices();
                            localisEventHere.addProcessCount(task.getwindowSlices());
                            //home many windows if there is a long gap
                            task.setWindowCount(
                                    (int)(timeTemp - task.getInitialTime()) / task.query.getSlide());
//                            localisEventHere.multipleWindowEndList[task.getTaskId()] =
//                                    (int) (timeTemp - task.getProcessTime() - task.query.getRange()) / task.query.getSlide() + 1;
                            //in case there is a super long gap
//                            task.setProcessTime(timeTemp - (timeTemp - task.getProcessTime() - task.query.getRange()) % task.query.getSlide()
//                                    - task.query.getRange() + task.query.getSlide());
                            task.setProcessTime(task.getInitialTime() + task.getWindowCount()*task.query.getSlide());
                        }
                        //window start
                        if ((timeTemp - task.getEventTime()) > task.query.getSlide()) {
                            //for the state
                            task.windowSliceAdd();
//                            localisEventHere.stateList[task.getTaskId()] = conf.EVENTSTART;
                            localisEventHere.setCreateNewWindow(true);
                            localisEventHere.processList[task.getTaskId()] = task.getwindowSlices();
                            localisEventHere.addProcessCount(task.getwindowSlices());
                            //home many windows if there is a long gap
//                                resultOfIsEventHere.multipleWindowEndList[task.getTaskId()] = (int) (timeTemp - task.getEventTime()) / task.query.getSlide();
                            //align and in case there is a long gap
                            task.setEventTime(timeTemp - (timeTemp - task.getEventTime()) % task.query.getSlide());
                        }
                    }
                    localisEventHere.setProcessWindow(true);
                    localisEventHere.functions[task.query.getFunction()] = true;
                    break;
                }
                //4-----session window
                case Configuration.SESSION: {
                    if (task.getProcessTime() == 0) {
                        task.setProcessTime(timeTemp);
                        //for the state
                        task.windowSliceAdd();
//                        localisEventHere.stateList[task.getTaskId()] = conf.EVENTSTART;
                        localisEventHere.setCreateNewWindow(true);
                        localisEventHere.processList[task.getTaskId()] = task.getwindowSlices();
                        localisEventHere.addProcessCount(task.getwindowSlices());
                    } else {
                        if (timeTemp - task.getProcessTime() > task.query.getSlide()) {
                            //there is a gap
                            localisEventHere.endList[task.getTaskId()] = true;
//                            localisEventHere.stateList[task.getTaskId()] = conf.EVENTENDANDSTART;
                            localisEventHere.setCreateNewWindow(true);
                            localisEventHere.setFinishWindow(true);
                            localisEventHere.processList[task.getTaskId()] = task.getwindowSlices();
                            localisEventHere.addProcessCount(task.getwindowSlices());
                            task.setWindowCount(task.getWindowCount() + 1);
//                            localisEventHere.multipleWindowEndList[task.getTaskId()] = 1;
                            //in case there is a super long gap
                            task.setProcessTime(timeTemp);
                        } else {
                            //for the state
//                            localisEventHere.stateList[task.getTaskId()] = conf.EVENTNOTHING;
                            task.setProcessTime(timeTemp);
                        }
                    }
                    localisEventHere.setProcessWindow(true);
                    localisEventHere.functions[task.query.getFunction()] = true;
                    break;
                }
                //5-----punctuation window
                case Configuration.PUNCTUATION: {
                    if (task.getProcessTime() == 0) {
                        task.setProcessTime(timeTemp);
                        //for the state
                        task.windowSliceAdd();
//                        localisEventHere.stateList[task.getTaskId()] = conf.EVENTSTART;
                        localisEventHere.setCreateNewWindow(true);
                        localisEventHere.processList[task.getTaskId()] = task.getwindowSlices();
                        localisEventHere.addProcessCount(task.getwindowSlices());
                    } else {
                        if (task.query.getEndPunctuation() == tuple.EVENT) {
                            //there is a gap
                            localisEventHere.endList[task.getTaskId()] = true;
//                            localisEventHere.stateList[task.getTaskId()] = conf.EVENTENDANDSTART;
                            localisEventHere.setCreateNewWindow(true);
                            localisEventHere.setFinishWindow(true);
                            localisEventHere.processList[task.getTaskId()] = task.getwindowSlices();
                            localisEventHere.addProcessCount(task.getwindowSlices());
//                            localisEventHere.multipleWindowEndList[task.getTaskId()] = 1;
                            task.setWindowCount(task.getWindowCount() + 1);
                            //in case there is a super long gap
                            task.setProcessTime(timeTemp);
                        }
                    }
                    localisEventHere.setProcessWindow(true);
                    localisEventHere.functions[task.query.getFunction()] = true;
                    break;
                }
                //6 -- count based window
                //in fact the first tuple which make decision to build
                // the sub window is not belong to this sub window, however here is a bug
                //so the tuple number is more than batchSize, which equals to batchsize + 1
                //batch size is process time
                case Configuration.COUNTBASED: {
                    if (task.getProcessTime() == 0) {
                        task.setProcessTime(tupleCounter);
                        //for the state
                        task.windowSliceAdd();
//                        localisEventHere.stateList[task.getTaskId()] = conf.EVENTSTART;
                        localisEventHere.setCreateNewWindow(true);
                        localisEventHere.processList[task.getTaskId()] = task.getwindowSlices();
                        localisEventHere.addProcessCount(task.getwindowSlices());
                    } else {
//                            if (tupleCounter - task.getProcessTime() > task.query.getBatchSize()) {
                        if (tupleCounter - task.getProcessTime() >= conf.centralizedBatchSize) {
                            //for the state
                            localisEventHere.endList[task.getTaskId()] = true;
//                            localisEventHere.stateList[task.getTaskId()] = conf.EVENTENDANDSTART;
                            localisEventHere.setCreateNewWindow(true);
                            localisEventHere.setFinishWindow(true);
                            localisEventHere.processList[task.getTaskId()] = task.getwindowSlices();
                            localisEventHere.addProcessCount(task.getwindowSlices());
//                            localisEventHere.multipleWindowEndList[task.getTaskId()] = 1;
                            task.setWindowCount(task.getWindowCount() + 1);
                            //align
                            task.setProcessTime(tupleCounter);
                        }
                    }
                    localisEventHere.setProcessWindow(true);
                    localisEventHere.functions[task.query.getFunction()] = true;
                    break;
                }
                default: {
                    break;
                }

            }
        });
        return localisEventHere;
    }

    private void createWindow(LocalisEventHere localisEventHere){
        //organize previous local window
//        if(!localWindows.isEmpty())
//            localWindows.getLast().tupleList.sort((a, b) -> Double.compare(a.DATA, b.DATA));

        //instance of new local window
        localWindowCounterIncrement();
        LocalWindow localWindow = new LocalWindow();
        localWindow.setWindowId(localWindowCounter);
        //record window slice
        localWindow.processList = localisEventHere.processList;
        localWindow.setWindowUsedCounter(localisEventHere.getProcessCount());
//        localWindow.tupleList = new ArrayList<Tuple>(conf.centralizedBatchSize);
        localWindow.count = 0;
        localWindow.sum = 0;
        localWindow.max = Double.MIN_VALUE;
        localWindow.min = Double.MAX_VALUE;
        functionBreakToOperators(localWindow, localisEventHere);
        localWindows.add(localWindow);
    }

    private void processWindow(LocalisEventHere localisEventHere, Tuple tuple) {
        //optimizer can calculate all the queries
        //decomposable function
        LocalWindow localWindow = localWindows.getLast();
        if(localWindow.operators[conf.COUNTOPERATOR]){
            localWindow.count++;
        }
        if(localWindow.operators[conf.SUMOPERATOR]){
            localWindow.sum += tuple.DATA;
        }
        if(localWindow.operators[conf.SINGLEMAXOPERATOR]){
            localWindow.max = Math.max(localWindow.max, tuple.DATA);
        }
        if(localWindow.operators[conf.SINGLEMINOPERATOR]){
            localWindow.min = Math.min(localWindow.min, tuple.DATA);
        }
        if(localWindow.operators[conf.SORTANDSTOREOPERATOR]){
            tupleList.add(tuple);
        }


        //batch tuples to send, because the maximum packet size of zeromp no more than 50000 bytes.
        //only when there is no countbased window
        //tuplelist size > 0 means there are median and quantile functions
        if(!hasCountBased && tupleList.size() >= conf.centralizedBatchSize){
            //the tuple list need to be sorted
            tupleList.sort((a, b) -> Double.compare(a.DATA, b.DATA));
            batchAndSending(localWindow);
        }
    }

    //batch median and quantile tuples
    void batchAndSending(LocalWindow localWindow){
        //iterate all local tasks that processed by this local window
        WindowCollection windowCollection = new WindowCollection();
        windowCollection.windowList = new ArrayList<>();
        windowCollection.tuples = tupleList;

        Window window = new Window();
//        window.queryId = ;
//        window.windowId = j;
//        window.count = j;
//        window.result = j;
        windowCollection.windowList.add(window);


        localTasks.forEach(task -> {
            switch (task.query.getFunction()){
                case Configuration.MAX: {
                    localWindow.max = Math.max(localWindow.max, tupleList.get(conf.centralizedBatchSize-1).DATA);
                    break;
                }
                case Configuration.MIN: {
                    localWindow.min = Math.min(localWindow.min, tupleList.get(0).DATA);
                    break;
                }
                case Configuration.MEDIAN: {
                    //window id for this query
//                    window.queryIdList[task.query.getQueryId()] = task.getWindowId();
                    break;
                }
                case Configuration.QUANTILE: {
                    //window id for this query
//                    window.queryIdList[task.query.getQueryId()] = task.getWindowCounter();
                    break;
                }
                default:
                    break;
            }
        });
//        windowList.add(window);
//        intermediateResultQueue.addAll(windowList);
//        windowList.clear();
        intermediateResultQueue.add(windowCollection);
        tupleList = new ArrayList<>(conf.centralizedBatchSize);
//        tupleList.clear();
//        System.out.println(tupleList.size());
    }

    void endWindow(LocalisEventHere localisEventHere) {
        WindowCollection windowCollection = new WindowCollection();
        windowCollection.windowList = new ArrayList<>();

        //we chance strategy that merges windows
        //iterate all local tasks
        localTasks.forEach(task -> {
            if (localisEventHere.endList[task.getTaskId()]){
                Window window = new Window();
                window.queryId = task.query.getQueryId();
                window.windowId = task.getWindowId();
                task.setWindowId(task.getWindowCount() + 1);
                localWindows.forEach(localWindow -> {
                    if (localWindow.processList[task.getTaskId()]>0) {
                        //merge results of local windows into window
                        mergeWindow(task, localWindow, window);
                        //remove the empty local windows and organize rest local windows
                        localWindow.processList[task.getTaskId()] -= 1;
                        localWindow.windowUsedCounterDelete();
                    }
                });
                windowCollection.windowList.add(window);
            }
        });
        //delete localwindow
        localWindows.removeIf(localWindow -> localWindow.getWindowUsedCounter() == 0);
        //send window
        intermediateResultQueue.add(windowCollection);


    }

    void mergeWindow(LocalTask task, LocalWindow localWindow, Window window){
        if(task.query.getScenario() == conf.DeCentralizedAggregation) {
            switch (task.query.getFunction()) {
                case Configuration.COUNT: {
                    window.count += localWindow.count;
                    break;
                }
                case Configuration.SUM: {
                    window.result += localWindow.sum;
                    break;
                }
                case Configuration.AVERAGE: {
                    window.count += localWindow.count;
                    window.result += localWindow.sum;
                    break;
                }
                case Configuration.MAX: {
                    if(nonDecomposable)
                        window.result = Math.max(window.result, tupleList.get(conf.centralizedBatchSize-1).DATA);
                    else
                        window.result = Math.max(window.result, localWindow.max);
                    break;
                }
                case Configuration.MIN: {
                    if(nonDecomposable)
                        window.result = Math.min(window.result, tupleList.get(0).DATA);
                    else
                        window.result = Math.min(window.result, localWindow.min);
                    break;
                }
                default:
                    break;
            }
        }
//        }else{
//            //merge two list
//            window.tuples.addAll(localWindow.tupleList);
//            window.tuples.sort((a, b) -> Double.compare(a.DATA, b.DATA));
//        }
    }

    private void tupleCounterIncrement(){
        tupleCounter++;
    }

    private void localWindowCounterIncrement(){
        localWindowCounter++;
    }

    private void functionBreakToOperators(LocalWindow localWindow, LocalisEventHere localisEventHere){
        localWindow.operators = new boolean[conf.OPERATORS];
        //to analyze operators
        if(localisEventHere.functions[conf.AVERAGE] | localisEventHere.functions[conf.COUNT]) {
            localWindow.operators[conf.COUNTOPERATOR] = true;
        }
        if(localisEventHere.functions[conf.AVERAGE] | localisEventHere.functions[conf.SUM]){
            localWindow.operators[conf.SUMOPERATOR] = true;
        }
        if(localisEventHere.functions[conf.QUANTILE] | localisEventHere.functions[conf.MEDIAN] ){
            localWindow.operators[conf.SORTANDSTOREOPERATOR] = true;
        }else {
            if (localisEventHere.functions[conf.MAX]) {
                localWindow.operators[conf.SINGLEMAXOPERATOR] = true;
            }
            if (localisEventHere.functions[conf.MIN]) {
                localWindow.operators[conf.SINGLEMINOPERATOR] = true;
            }
            if (localisEventHere.functions[conf.NON]) {
                localWindow.operators[conf.SORTANDSTOREOPERATOR] = true;
            }
        }
    }

    private void queryPreProcess(){
        //get all queries, it will be skipped when all queries retrieved
        while(localTasks.size() < conf.queryNumber){
            if(!queryQueue.isEmpty()){
                LocalTask task = new LocalTask();
                task.query = (Query) queryQueue.poll();
                task.setTaskId(task.query.getQueryId());
                //window counter should start from 1,
                // since we use windowId=0 to decide if this query output result to parent
                task.setWindowId(1);
                localTasks.add(task);

                //to regulate program
                if(task.query.getFunction() == conf.MEDIAN || task.query.getFunction() == conf.QUANTILE ){
                    this.nonDecomposable = true;
                }

                if(task.query.getWindowType() == conf.COUNTBASED){
                    this.hasCountBased = true;
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
            LocalTask task = new LocalTask();
            task.query = (Query) queryQueue.poll();
            task.setTaskId(task.query.getQueryId());
            localTasks.add(task);
        }

    }

}
