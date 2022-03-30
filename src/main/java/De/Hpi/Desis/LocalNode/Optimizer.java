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
    private boolean[] operators;

    //flags for organize()
    //there are non-decomposable function and system has to sort data anyway
    private boolean sortFLAG;
    //there are countbased window
    private boolean preserveFLAG;
    private boolean countbasedFLAG;
    private boolean maxFLAG;
    private boolean minFLAG;

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
        this.operators = new boolean[conf.OPERATORS];
        this.sortFLAG = false;
        this.preserveFLAG = false;
        this.countbasedFLAG = false;
        this.maxFLAG = false;
        this.minFLAG = false;
    }

    public void run() {
//        final long[] time1 = new long[1];
//        final long[] time2 = new long[1];
//        final long[] time3 = new long[1];
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

//                    long timeTemp1 = System.nanoTime();
                    if(localisEventHere.isFinishWindow()){
                        endWindow(localisEventHere.isFinishWindow());
                    }
//                    time1[0] = time1[0] + System.nanoTime() - timeTemp1;

//                    timeTemp1 = System.nanoTime();
                    if(localisEventHere.isCreateNewWindow()){
                        createWindow(localisEventHere);
                    }
//                    time2[0] = time2[0] + System.nanoTime() - timeTemp1;

//                    timeTemp1 = System.nanoTime();
                    if(localisEventHere.isProcessWindow()){
                        //calculate the result the calculate action
                        processWindow(tuple);
                    }
//                    time3[0] = time3[0] + System.nanoTime() - timeTemp1;

                });
//                System.out.println(time1[0]);
//                System.out.println(time2[0]);
//                System.out.println(time3[0]);
//                System.out.println();
            }

        }
    }

    //the end tuple of a window always belongs to next window,
    private LocalisEventHere isEventHere(Tuple tuple, long timeTemp){
        tupleCounterIncrement();
        //to record the window state of each query
        LocalisEventHere localisEventHere = new LocalisEventHere();
        //to break down functions into operators
//        localisEventHere.functions = new boolean[conf.FUNCTIONS];
        //the window of this query is end and need to be calculated
//        localisEventHere.endList = new boolean[localTasks.size()];
        //the window of this query is processing
        localisEventHere.processList = new int[localTasks.size()];
        //mark down the current state of window that belong to this query
//        localisEventHere.stateList = new int[localTasks.size()];
        //in case there is a long gap and multiple windows end
//        localisEventHere.multipleWindowEndList = new int[localTasks.size()];

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
                        //initial tumbing window
                        task.setProcessTime(timeTemp);
                        task.setInitialTime(timeTemp);
                    } else {
                        if ((timeTemp - task.getProcessTime()) > task.query.getRange()) {
                            localisEventHere.setCreateNewWindow(true);
                            localisEventHere.setFinishWindow(true);
                            task.setWindowEnd(true);
                            task.setWindowCount((int)(timeTemp - task.getInitialTime()) / task.query.getRange());
//                            localisEventHere.multipleWindowEndList[task.getTaskId()] =
//                                    (int) (timeTemp - task.getInitialTime()) / task.query.getRange();
                            //in case there is a super long gap
                            task.setProcessTime(task.getInitialTime() + task.getWindowCount()*task.query.getRange());
                            //task.setProcessTime(timeTemp - (timeTemp - task.getProcessTime()) % task.query.getRange());
                        }
                    }
                    //the new slice includes all queries that are processing
                    if(task.query.getScenario() == conf.DeCentralizedAggregation) {
                        localisEventHere.processList[task.getTaskId()] = task.getwindowSlices();
                        localisEventHere.addProcessCount(task.getwindowSlices());
                    }
//                    localisEventHere.functions[task.query.getFunction()] = true;
                    localisEventHere.setProcessWindow(true);
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
                        task.setProcessTime(timeTemp);
                        task.setEventTime(timeTemp);
                        task.setInitialTime(timeTemp);
                    } else {
                        //window end
                        if ((timeTemp - task.getProcessTime()) > task.query.getRange()) {
                            //for the state
                            task.windowSliceDelete();
                            localisEventHere.setCreateNewWindow(true);
                            localisEventHere.setFinishWindow(true);
                            task.setWindowEnd(true);
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
                            //home many windows if there is a long gap
//                                resultOfIsEventHere.multipleWindowEndList[task.getTaskId()] = (int) (timeTemp - task.getEventTime()) / task.query.getSlide();
                            //align and in case there is a long gap
                            task.setEventTime(timeTemp - (timeTemp - task.getEventTime()) % task.query.getSlide());
                        }
                    }
                    if(task.query.getScenario() == conf.DeCentralizedAggregation) {
                        localisEventHere.processList[task.getTaskId()] = task.getwindowSlices();
                        localisEventHere.addProcessCount(task.getwindowSlices());
                    }
//                    localisEventHere.functions[task.query.getFunction()] = true;
                    localisEventHere.setProcessWindow(true);
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
                    } else {
                        if (timeTemp - task.getProcessTime() > task.query.getSlide()) {
                            //there is a gap
//                            localisEventHere.stateList[task.getTaskId()] = conf.EVENTENDANDSTART;
                            localisEventHere.setCreateNewWindow(true);
                            localisEventHere.setFinishWindow(true);
                            task.setWindowEnd(true);
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
                    if(task.query.getScenario() == conf.DeCentralizedAggregation) {
                        localisEventHere.processList[task.getTaskId()] = task.getwindowSlices();
                        localisEventHere.addProcessCount(task.getwindowSlices());
                    }
//                    localisEventHere.functions[task.query.getFunction()] = true;
                    localisEventHere.setProcessWindow(true);
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
                    } else {
                        if (task.query.getEndPunctuation() == tuple.EVENT) {
                            //there is a gap
//                            localisEventHere.stateList[task.getTaskId()] = conf.EVENTENDANDSTART;
                            localisEventHere.setCreateNewWindow(true);
                            localisEventHere.setFinishWindow(true);
                            task.setWindowEnd(true);
//                            localisEventHere.multipleWindowEndList[task.getTaskId()] = 1;
                            task.setWindowCount(task.getWindowCount() + 1);
                            //in case there is a super long gap
                            task.setProcessTime(timeTemp);
                        }
                    }
                    if(task.query.getScenario() == conf.DeCentralizedAggregation) {
                        localisEventHere.processList[task.getTaskId()] = task.getwindowSlices();
                        localisEventHere.addProcessCount(task.getwindowSlices());
                    }
//                    localisEventHere.functions[task.query.getFunction()] = true;
                    localisEventHere.setProcessWindow(true);
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
                    } else {
//                            if (tupleCounter - task.getProcessTime() > task.query.getBatchSize()) {
                        if (tupleCounter - task.getProcessTime() >= conf.centralizedBatchSize) {
                            //for the state
//                            localisEventHere.stateList[task.getTaskId()] = conf.EVENTENDANDSTART;
                            localisEventHere.setCreateNewWindow(true);
                            localisEventHere.setFinishWindow(true);
                            task.setWindowEnd(true);
//                            localisEventHere.multipleWindowEndList[task.getTaskId()] = 1;
                            task.setWindowCount(task.getWindowCount() + 1);
                            //align
                            task.setProcessTime(tupleCounter);
                        }
                    }
//                    localisEventHere.processList[task.getTaskId()] = task.getwindowSlices();
//                    localisEventHere.addProcessCount(task.getwindowSlices());
//                    localisEventHere.functions[task.query.getFunction()] = true;
                    localisEventHere.setProcessWindow(true);
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
        localWindow.setLocalWindowId(localWindowCounter);
        //record window slice
        localWindow.processList = localisEventHere.processList;
        localWindow.setLocalWindowCounter(localisEventHere.getProcessCount());
//        localWindow.tupleList = new ArrayList<Tuple>(conf.centralizedBatchSize);
        localWindow.count = 0;
        localWindow.sum = 0;
        localWindow.max = Double.MIN_VALUE;
        localWindow.min = Double.MAX_VALUE;
        //send batch when slice end
        if(tupleList.size() > 0){
            endWindow(false);

        }
        localWindows.add(localWindow);
    }

    private void processWindow(Tuple tuple) {
        //optimizer can calculate all the queries
        //decomposable function
        LocalWindow localWindow = localWindows.getLast();
        if(this.operators[conf.COUNTOPERATOR]){
            localWindow.count++;
        }
        if(this.operators[conf.SUMOPERATOR]){
            localWindow.sum += tuple.DATA;
        }
        if(this.operators[conf.MAXOPERATOR]){
            localWindow.max = Math.max(localWindow.max, tuple.DATA);
        }
        if(this.operators[conf.MINOPERATOR]){
            localWindow.min = Math.min(localWindow.min, tuple.DATA);
        }
        if(this.operators[conf.SORTOPERATOR] | this.operators[conf.PRESERVEOPERATOR]){
            tupleList.add(tuple);
        }

        //batch tuples to send, because the maximum packet size of zeromp no more than 50000 bytes.
        //only when there is no countbased window
        //tuplelist size > 0 means there are median and quantile functions
        if(!countbasedFLAG && tupleList.size() >= conf.centralizedBatchSize){
            endWindow(false);
        }
    }

    void endWindow(boolean isWindowEnd) {
//        long time1 = 0;
//        long time2 = 0;
//        long time3 = 0;
        WindowCollection windowCollection = new WindowCollection();
        windowCollection.windowList = new ArrayList<>();
//        System.out.println(tupleList.size());
        //sort -> store, sort based on store
        //for decentralized aggregation
//        long timeTemp = System.nanoTime();
        organizeWinodw(windowCollection);
//        time1 = time1 + System.nanoTime() - timeTemp;
//        timeTemp = System.nanoTime();
        //we chance strategy that merges windows
        //iterate all local tasks
        localTasks.forEach(task -> {
            if (task.getWindowEnd()){
                Window window = new Window();
                window.queryId = task.query.getQueryId();
                window.windowId = task.getWindowId();
                task.setWindowId(task.getWindowCount() + 1);
                task.setWindowEnd(false);
                //we dont new a localwindow for centralized aggregation
                if(task.query.getScenario() == conf.DeCentralizedAggregation) {
                    localWindows.forEach(localWindow -> {
                        if (localWindow.processList[task.getTaskId()] > 0) {
                            //merge results of local windows into window
                            mergeWindow(task, localWindow, window);
                            //remove the empty local windows and organize rest local windows
                            localWindow.processList[task.getTaskId()] -= 1;
                            localWindow.windowUsedCounterDelete();
                        }
                    });
                }
                windowCollection.windowList.add(window);
            } else{
                //batch and send median, quantile and countbased even if they are not end
                if(task.query.getScenario() == conf.CentralizedAggregation){
                    Window window = new Window();
                    window.queryId = task.query.getQueryId();
                    window.windowId = task.getWindowId();
                    windowCollection.windowList.add(window);
                }
            }
        });
//        time2 = time2 + System.nanoTime() - timeTemp;
//        timeTemp = System.nanoTime();
        //delete localwindow
        if(isWindowEnd)
            localWindows.removeIf(localWindow -> localWindow.getLocalWindowCounter() <= 0);
//        time3 = time3 + System.nanoTime() - timeTemp;

//        System.out.println(time1);
//        System.out.println(time2);
//        System.out.println(time3);
//        System.out.println(localWindows.size());
//        System.out.println();

        //send window
        intermediateResultQueue.add(windowCollection);
    }

    void organizeWinodw(WindowCollection windowCollection){
        //there is at least one centralized query
        if(preserveFLAG){
            windowCollection.tuples = tupleList;
            tupleList = new ArrayList<>(conf.centralizedBatchSize);
            if(sortFLAG){
                windowCollection.tuples.sort((a, b) -> Double.compare(a.DATA, b.DATA));
                if(maxFLAG)
                    localWindows.getLast().max = Math.max(localWindows.getLast().max
                            , windowCollection.tuples.get(conf.centralizedBatchSize-1).DATA);
                if(minFLAG)
                    localWindows.getLast().min = Math.min(localWindows.getLast().min
                            , windowCollection.tuples.get(0).DATA);
            }
        }
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
                        window.result = localWindow.max;
                    break;
                }
                case Configuration.MIN: {
                        window.result = localWindow.min;
                    break;
                }
                default:
                    break;
            }
        }
    }

    private void tupleCounterIncrement(){
        tupleCounter++;
    }

    private void localWindowCounterIncrement(){
        localWindowCounter++;
    }

    private void functionBreakToOperators(int function){
        //to analyze operators
        if(function == conf.AVERAGE || function == conf.COUNT) {
            this.operators[conf.COUNTOPERATOR] = true;
        }
        if(function == conf.AVERAGE || function == conf.SUM) {
            this.operators[conf.SUMOPERATOR] = true;
        }
        if(function == conf.QUANTILE || function == conf.MEDIAN ){
            this.operators[conf.SORTOPERATOR] = true;
        }else {
            if (function == conf.MAX) {
                this.operators[conf.MAXOPERATOR] = true;
            }
            if (function == conf.MIN) {
                this.operators[conf.MINOPERATOR] = true;
            }
            if (function == conf.NON) {
                this.operators[conf.PRESERVEOPERATOR] = true;
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

                //flags are for organizing windows
                //to regulate program, sort -> store,
                if(task.query.getFunction() == conf.MEDIAN || task.query.getFunction() == conf.QUANTILE ){
                    this.sortFLAG = true;
                }
                if(task.query.getScenario() == conf.CentralizedAggregation){
                    this.preserveFLAG = true;
                }
                if(task.query.getWindowType() == conf.COUNTBASED){
                    this.countbasedFLAG = true;
                }
                if(task.query.getScenario() == conf.MAX){
                    this.maxFLAG = true;
                }
                if(task.query.getScenario() == conf.MIN){
                    this.minFLAG = true;
                }

                //for countbased window, we need to save data anyway
                //if there are not meidan aor quantile, we dont perform sorting
                if(!(task.query.getFunction() == conf.MEDIAN || task.query.getFunction() == conf.QUANTILE)
                        && task.query.getWindowType() == conf.COUNTBASED){
                    task.query.setFunction(conf.NON);
                }

                //analyze aggregation function operators
                functionBreakToOperators(task.query.getFunction());



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
