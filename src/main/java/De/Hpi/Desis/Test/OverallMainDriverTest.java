package De.Hpi.Desis.Test;

import De.Hpi.Desis.EntryPoint.EnteryPointForIntermediaNode;
import De.Hpi.Desis.EntryPoint.EnteryPointForRootnode;
import De.Hpi.Desis.EntryPoint.EntryPointForLocalNode;

public class OverallMainDriverTest {

    public static void main(String[] args) throws Exception
    {
        EnteryPointForRootnode.main(new String[]{"0", "0"});

        EnteryPointForIntermediaNode.main(new String[]{"1", "0"});
//        EntryPointForLocalNode.main(new String[]{"1"});

        EntryPointForLocalNode.main(new String[]{"2", "0"});
//        EnteryPointForIntermediaNode.main(new String[]{"2"});
//        EntryPointForLocalNode.main(new String[]{"3"});
//        EntryPointForLocalNode.main(new String[]{"4"});
    }

}
