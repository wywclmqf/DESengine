package De.Hpi.Desis.MessageManager;

import De.Hpi.Desis.Configure.Configuration;
import De.Hpi.Desis.Dao.Tuple;
import De.Hpi.Desis.Dao.Window;
import De.Hpi.Desis.Dao.WindowCollection;
import De.Hpi.Desis.Message.MessageResult;
import org.msgpack.MessagePack;
import org.zeromq.ZMQ;

import java.io.IOException;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RootSubscribeMassage implements Runnable{

    private Configuration conf;
    private ConcurrentLinkedQueue<WindowCollection> resultFromIntermediaDecentral;
    private ConcurrentLinkedQueue<Tuple> resultFromIntermediaCentral;
    private ZMQ.Socket socketSub;

    public RootSubscribeMassage(ConcurrentLinkedQueue<WindowCollection> resultFromIntermediaDecentral
            , ConcurrentLinkedQueue<Tuple> resultFromIntermediaCentral
            , Configuration conf, ZMQ.Socket socketSub) {
        this.resultFromIntermediaDecentral =resultFromIntermediaDecentral;
        this.resultFromIntermediaCentral =resultFromIntermediaCentral;
        this.socketSub = socketSub;
        this.conf = conf;
    }

    public void run() {
        MessagePack msgpack = new MessagePack();
        socketSub.subscribe("".getBytes());
        long tupleCounter = 0;
        long networkOverheadR = 0;
        long networkOverheadI = 0;
        long begintime = System.currentTimeMillis();
        long endtime = System.currentTimeMillis();

        while (true) {
            byte[] raw = socketSub.recv(1);
            if(raw!=null) {
                try {
                    MessageResult messageResult = msgpack.read(raw,
                            MessageResult.class);

                    resultFromIntermediaDecentral.offer(messageResult.windowCollection);
                    if(conf.DEBUGMODE_ROOT) {
                        if(tupleCounter == 0){
                            tupleCounter++;
                            networkOverheadR = getNetworkOverheadR(raw.length);
                            networkOverheadI = getNetworkOverheadI(raw.length);
                            begintime = System.currentTimeMillis();
                            endtime = System.currentTimeMillis();
                            continue;
                        }
                        tupleCounter++;
                        networkOverheadR+=getNetworkOverheadR(raw.length);
                        networkOverheadI+=getNetworkOverheadI(raw.length);
                        if (System.currentTimeMillis() - endtime > conf.BenchMarkOutputFrequency) {
                            endtime = System.currentTimeMillis();
                            System.out.println("rootNode--INFO"
                                    + "  Throughput:  " + tupleCounter / ((endtime - begintime) / 1000.0)
                                    + "  BandWidth(Root):  " + networkOverheadR  / ((endtime - begintime) / 1000.0)
                                    + "  BandWidth(Inter):  " + networkOverheadI  / ((endtime - begintime) / 1000.0)
                                    + "  Allcounter:  " + tupleCounter
                                    + "  NetworkOverhead(Root):  " + networkOverheadR
                                    + "  NetworkOverhead(Inter):  " + networkOverheadI
                                    + "  Time:  " + (endtime - begintime) / 1000.0
                                    + "  GCTime:  " + getGarbageCollectionTime()
                                    + "  GC/Time-Ratio:  " + (double) getGarbageCollectionTime() / (endtime - begintime)
                            );
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static long getGarbageCollectionTime() {
        long collectionTime = 0;
        for (GarbageCollectorMXBean garbageCollectorMXBean : ManagementFactory.getGarbageCollectorMXBeans()) {
            collectionTime += garbageCollectorMXBean.getCollectionTime();
        }
        return collectionTime;
    }
    private static long getNetworkOverheadR(int rawSize) {
        return (rawSize / (9000 - 46) + 1) * (44) + (44 + 44);
    }
    private static long getNetworkOverheadI(int rawSize) {
        return (rawSize / (9000 - 46) + 1) * (46) + (45 + 45) + rawSize;
    }
}
