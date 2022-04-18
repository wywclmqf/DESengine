package De.Hpi.DesisCen.Test;

import De.Hpi.DesisCen.EntryPoint.EnteryPointForRootnode;
import De.Hpi.DesisCen.EntryPoint.EntryPointForLocalNode;

public class OverallMainDriverTestCen {

    public static void main(String[] args) throws Exception
    {
        EnteryPointForRootnode.main(new String[]{"0", "100"});

        EntryPointForLocalNode.main(new String[]{"1", "100"});
//        EntryPointForLocalNode.main(new String[]{"2"});
//        EntryPointForLocalNode.main(new String[]{"3"});
//        EntryPointForLocalNode.main(new String[]{"4"});

//        EntryPointForLocalNode.main(new String[]{"2"});
//
//        EnteryPointForIntermediaNode.main(new String[]{"2"});
//        EntryPointForLocalNode.main(new String[]{"3"});
//        EntryPointForLocalNode.main(new String[]{"4"});
    }

}
