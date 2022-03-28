package De.Hpi.Desis.Dao;

import org.msgpack.annotation.Message;

import java.util.ArrayList;

@Message
public class Window{

//    private int windowId;
    public int nodeId;
    public int[] queryIdList;
    public int queryId;
//    public int Scenario;

    //intermediate results
    public double result;
    public long count;
    public ArrayList<Tuple> tuples;

//    public int getWindowId() {
//        return windowId;
//    }
//    public void setWindowId(int windowId) {
//        this.windowId = windowId;
//    }
//    public void setNodeId(int nodwId) {
//        this.nodeId = nodwId;
//    }
//    public int getNodeId() {
//        return nodeId;
//    }
//    public int getQueryId() {
//        return queryId;
//    }
//    public void setQueryId(int queryId) {
//        this.queryId = queryId;
//    }
//    public int getScenario() {
//        return Scenario;
//    }
//    public void setScenario(int scenario) {
//        Scenario = scenario;
//    }

}
