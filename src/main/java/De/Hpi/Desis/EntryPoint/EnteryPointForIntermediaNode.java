package De.Hpi.Desis.EntryPoint;

import De.Hpi.Desis.Configure.Configuration;
import De.Hpi.Desis.IntermediaNode.IntermediaNode;

public class EnteryPointForIntermediaNode {
    public static void main(String[] args) {
        Configuration conf = new Configuration();
        IntermediaNode intermediaNode = new IntermediaNode(conf, Integer.valueOf(args[0]));
    }
}
