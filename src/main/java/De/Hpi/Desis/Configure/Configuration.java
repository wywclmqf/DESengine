package De.Hpi.Desis.Configure;

public class Configuration implements ConfigurationTopology, ConfigurationWindow,
        ConfigurationProcessing, ConfigurationGenerator, ConfigurationMessage
        , ConfigurationBenchmark{

    //to calculate how to slice sliding window
    //a sliding window can be sliced into  (previous window end punctuation + next window start punctuation)
    //Q1: how many slices in a sliding window?
    //s1: range / slide < 1, slice = 3
    //s2: range / slide = 2, slice = 2 & range / slide = x, slice = x
    //s3: range / slide = 1.x & 2.x slice = ?
    //Q2: with size of window going large, the throughput also going high
    //Q3: the hopping window slicing & optimization?

    //how many query we would simulate
    public int queryNumber = 100;
    //to make program easy
    // in optimizer system start to process only when "queryNumber" queries in system,
    // and querywait is to block loops
    public static final int queryWait = 10;
    //the batch size of centralized aggregation default 1000 * 1000 = 100w
    public static final int centralizedBatchSize = 1000;
    public static final int transferBatchSize = 1000;
    //expired time
    public static final int EXPIREDTIME = 1000; //watermark of intermediate window and root window
    //debug mode, output much more information, mainly print message between nodes
    public static final boolean DEBUGMODE_ROOT = true;
    public static final boolean DEBUGMODE_INTER = true;
    public static final boolean DEBUGMODE_LOCAL = true;
    //windows & Linuxs
    public static final boolean WINDOWS = true;
    //how many threads for generator
    public static final int GeneratorThreadNumber = 2;

    //node id
    private int nodeId;

    public int getNodeId() {
        return nodeId;
    }
    public void setNodeId(int nodeNumber) {
        this.nodeId = nodeNumber;
    }
}
