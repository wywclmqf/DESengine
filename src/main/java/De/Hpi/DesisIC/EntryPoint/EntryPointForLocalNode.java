package De.Hpi.DesisIC.EntryPoint;

import De.Hpi.DesisIC.Configure.Configuration;
import De.Hpi.DesisIC.LocalNode.LocalNode;

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
