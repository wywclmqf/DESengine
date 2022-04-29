package De.Hpi.DesisSW.Dao;

public class LocalWindow{

    private long localWindowId;
    //it decide when to delete this localwindow (is it still used by task)
    private int localWindowCounter;
    //the tasks that need this slice
    public int[] processList;

    //intermediate result
    public double sum;
    public double avgSum;
    public long count;
    public long avgCount;
    public double max;
    public double min;
//    public ArrayList<Tuple> tupleList;

    public long getLocalWindowId() {
        return localWindowId;
    }
    public void setLocalWindowId(long localWindowId) {
        this.localWindowId = localWindowId;
    }
    public int getLocalWindowCounter() {
        return localWindowCounter;
    }
    public void setLocalWindowCounter(int windowCounter) {
        this.localWindowCounter = windowCounter;
    }
    public void windowUsedCounterDelete(){
        this.localWindowCounter--;
    }

}
