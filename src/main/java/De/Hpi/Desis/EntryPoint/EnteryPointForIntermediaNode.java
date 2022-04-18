package De.Hpi.Desis.EntryPoint;

import De.Hpi.Desis.Configure.Configuration;
import De.Hpi.Desis.IntermediaNode.IntermediaNode;

public class EnteryPointForIntermediaNode {
    public static void main(String[] args) {
        Configuration conf = new Configuration();
        int queryNumber = conf.queryNumber;
        if(Integer.valueOf(args[1]) != 0){
            conf.queryNumber = Integer.valueOf(args[1]);
        }
        IntermediaNode intermediaNode = new IntermediaNode(conf, Integer.valueOf(args[0]));
    }
}
