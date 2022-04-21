package De.Hpi.DesisSW.EntryPoint;

import De.Hpi.DesisSW.Configure.Configuration;
import De.Hpi.DesisSW.LocalNode.LocalNode;

public class EntryPointForLocalNode {

    public static void main(String[] args)
    {
        Configuration conf = new Configuration();
        int queryNumber = conf.queryNumber;
        if(Integer.valueOf(args[1]) != 0){
            conf.queryNumber = Integer.valueOf(args[1]);
        }
        LocalNode localNode = new LocalNode(conf, Integer.valueOf(args[0]));
    }

}
