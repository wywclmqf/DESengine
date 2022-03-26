package De.Hpi.Desis.EntryPoint;

import De.Hpi.Desis.Configure.Configuration;
import De.Hpi.Desis.RootNode.RootNode;

public class EnteryPointForRootnode {

    public static void main(String[] args)
    {
        Configuration conf = new Configuration();
        RootNode rootNode = new RootNode(conf, Integer.valueOf(args[0]));
    }

}
