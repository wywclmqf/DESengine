package De.Hpi.DesisIC.Test;

import De.Hpi.DesisIC.EntryPoint.EnteryPointForIntermediaNode;
import De.Hpi.DesisIC.EntryPoint.EnteryPointForRootnode;
import De.Hpi.DesisIC.EntryPoint.EntryPointForLocalNode;

public class OverallMainDriverTest {

    public static void main(String[] args) throws Exception
    {
        EnteryPointForRootnode.main(new String[]{"1", "100"});

        EnteryPointForIntermediaNode.main(new String[]{"1", "100"});
//        EntryPointForLocalNode.main(new String[]{"1"});

        EntryPointForLocalNode.main(new String[]{"1", "100"});
//        EnteryPointForIntermediaNode.main(new String[]{"2"});
//        EntryPointForLocalNode.main(new String[]{"3"});
//        EntryPointForLocalNode.main(new String[]{"4"});
    }

}
