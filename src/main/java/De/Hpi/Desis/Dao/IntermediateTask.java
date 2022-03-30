package De.Hpi.Desis.Dao;

import java.util.LinkedList;

public class IntermediateTask {

    private int taskId;
    public Query query;
    public LinkedList<IntermediateWindow> intermediateWindowLinkedList;
    //it is from 1
    private int windowCounter;

    public int getTaskId() { return taskId; }
    public void setTaskId(int taskId) { this.taskId = taskId; }
    public int getWindowCounter() {
        return windowCounter;
    }
    public void setWindowCounter(int windowCounter) {
        this.windowCounter = windowCounter;
    }
    public void windowCounterAdd(){
        this.windowCounter++;
    }
    public void windowCounterDelete(){this.windowCounter--;}

}
