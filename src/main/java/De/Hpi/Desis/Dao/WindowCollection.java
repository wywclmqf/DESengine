package De.Hpi.Desis.Dao;

import org.msgpack.annotation.Message;

import java.util.ArrayList;

@Message
public class WindowCollection {

    //    private int windowId;
    public int nodeId;
    public ArrayList<Window> windowList;
    public ArrayList<Tuple> tuples;
}
