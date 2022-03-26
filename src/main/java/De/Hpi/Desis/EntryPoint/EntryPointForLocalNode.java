package De.Hpi.Desis.EntryPoint;

import De.Hpi.Desis.Configure.Configuration;
import De.Hpi.Desis.LocalNode.LocalNode;

public class EntryPointForLocalNode {

    public static void main(String[] args)
    {
        Configuration conf = new Configuration();
        LocalNode localNode = new LocalNode(conf, Integer.valueOf(args[0]));
    }

}
