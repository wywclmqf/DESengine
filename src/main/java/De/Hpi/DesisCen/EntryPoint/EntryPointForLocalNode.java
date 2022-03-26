package De.Hpi.DesisCen.EntryPoint;

import De.Hpi.DesisCen.Configure.Configuration;
import De.Hpi.DesisCen.LocalNode.LocalNode;

public class EntryPointForLocalNode {

    public static void main(String[] args)
    {
        Configuration conf = new Configuration();
        LocalNode localNode = new LocalNode(conf, Integer.valueOf(args[0]));
    }

}
