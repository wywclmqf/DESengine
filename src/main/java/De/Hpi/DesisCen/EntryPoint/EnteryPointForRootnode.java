package De.Hpi.DesisCen.EntryPoint;

import De.Hpi.DesisCen.Configure.Configuration;
import De.Hpi.DesisCen.RootNode.RootNode;

public class EnteryPointForRootnode {

    public static void main(String[] args)
    {
        Configuration conf = new Configuration();
        RootNode rootNode = new RootNode(conf, Integer.valueOf(args[0]));
    }

}
