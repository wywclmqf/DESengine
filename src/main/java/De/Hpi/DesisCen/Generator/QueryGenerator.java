package De.Hpi.DesisCen.Generator;

import De.Hpi.DesisCen.Configure.Configuration;
import De.Hpi.DesisCen.Dao.Query;

import java.util.concurrent.ConcurrentLinkedQueue;

public class QueryGenerator {

    private ConcurrentLinkedQueue<Query> queryQueue;
    private ConcurrentLinkedQueue<Query> queryList;
    private Configuration conf;
    private int queryCounter;
    private boolean flag;

    public QueryGenerator(ConcurrentLinkedQueue<Query> queryQueue,
                          ConcurrentLinkedQueue<Query> queryList,
                          Configuration conf){
        this.conf = conf;
        this.queryQueue = queryQueue;
        this.queryList = queryList;
        this.queryCounter = 0;
    }

    public void generate(){

        try {
            //totally 20 queries
            //object, function, windowType, range, slide, startPunctuation, endPunctuation, warterMark, Batch size
//            initializeQuery(Configuration.SPEED, Configuration.MEDIAN, Configuration.TUMBING,
//                    1000, 0, 0, 0, 0, 0);
            for(int i = 0; i <= 1000; i++){
                initializeQuery(Configuration.SPEED, Configuration.AVERAGE, Configuration.TUMBING,
                        1000*(i%10 + 1), 2000, 0, 0, 0, 0);
//                System.out.println(1000*(i%10 + 1));
            }
//            initializeQuery(Configuration.SPEED,Configuration.AVERAGE,Configuration.TUMBING,
//                    1000, 0, 0, 0, 0, 0);
//            initializeQuery(Configuration.SPEED,Configuration.MAX,Configuration.SLIDING,
//                    2000, 1000, 0, 0, 0, 0);
//            initializeQuery(Configuration.SPEED,Configuration.MEDIAN,Configuration.SLIDING_UNEVEN,
//                    1000, 2000, 0, 0, 0, 0);
//            initializeQuery(Configuration.SPEED,Configuration.AVERAGE,Configuration.SESSION,
//                    0, 1000,0 , 0, 5000, 0);
//            initializeQuery(Configuration.SPEED,Configuration.AVERAGE,Configuration.PUNCTUATION,
//                    0, 0, 0, 1, 5000, 0);
//            initializeQuery(Configuration.SPEED,Configuration.AVERAGE,Configuration.COUNTBASED,
//                    0, 0, 0, 0, 0, 1000000);

//            initializeQuery(Configuration.SPEED,Configuration.AVERAGE,Configuration.TUMBING,
//                    1000, 0, 0, 0, 0, 0);
//            initializeQuery(Configuration.SPEED,Configuration.AVERAGE,Configuration.SLIDING,
//                    2000, 1000, 0, 0, 0, 0);
//            initializeQuery(Configuration.SPEED,Configuration.AVERAGE,Configuration.SLIDING_UNEVEN,
//                    1000, 2000, 0, 0, 0, 0);
//            initializeQuery(Configuration.SPEED,Configuration.AVERAGE,Configuration.SESSION,
//                    0, 1000,0 , 0, 5000, 0);
//            initializeQuery(Configuration.SPEED,Configuration.AVERAGE,Configuration.PUNCTUATION,
//                    0, 0, 0, 1, 5000, 0);
//            initializeQuery(Configuration.SPEED,Configuration.AVERAGE,Configuration.COUNTBASED,
//                    0, 0, 0, 0, 0, 1000000);

//            initializeQuery(Configuration.SPEED,Configuration.MAX,Configuration.TUMBING,
//                    1000, 0, 0, 0, 0, 0);
//            initializeQuery(Configuration.SPEED,Configuration.MAX,Configuration.SLIDING,
//                    4000, 2000, 0, 0, 0, 0);
//            initializeQuery(Configuration.SPEED,Configuration.MAX,Configuration.SLIDING_UNEVEN,
//                    1000, 2000, 0, 0, 0, 0);
//            initializeQuery(Configuration.SPEED,Configuration.MAX,Configuration.SESSION,
//                    0, 1000,0 , 0, 5000, 0);
//            initializeQuery(Configuration.SPEED,Configuration.MAX,Configuration.PUNCTUATION,
//                    0, 0, 0, 1, 5000, 0);
//            initializeQuery(Configuration.SPEED,Configuration.MAX,Configuration.COUNTBASED,
//                    0, 0, 0, 0, 0, 1000000);

//            initializeQuery(Configuration.SPEED,Configuration.MIN,Configuration.TUMBING,
//                    1000, 0, 0, 0, 0, 0);
//            initializeQuery(Configuration.SPEED,Configuration.MIN,Configuration.SLIDING,
//                    4000, 2000, 0, 0, 0, 0);
//            initializeQuery(Configuration.SPEED,Configuration.MIN,Configuration.SLIDING_UNEVEN,
//                    1000, 2000, 0, 0, 0, 0);
//            initializeQuery(Configuration.SPEED,Configuration.MIN,Configuration.SESSION,
//                    0, 1000,0 , 0, 5000, 0);
//            initializeQuery(Configuration.SPEED,Configuration.MIN,Configuration.PUNCTUATION,
//                    0, 0, 0, 1, 5000, 0);
//            initializeQuery(Configuration.SPEED,Configuration.MIN,Configuration.COUNTBASED,
//                    0, 0, 0, 0, 0, 1000000);

//            initializeQuery(Configuration.SPEED,Configuration.SUM,Configuration.TUMBING,
//                    1000, 0, 0, 0, 0, 0);
//            initializeQuery(Configuration.SPEED,Configuration.SUM,Configuration.SLIDING,
//                    4000, 2000, 0, 0, 0, 0);
//            initializeQuery(Configuration.SPEED,Configuration.SUM,Configuration.SLIDING_UNEVEN,
//                    1000, 2000, 0, 0, 0, 0);
//            initializeQuery(Configuration.SPEED,Configuration.SUM,Configuration.SESSION,
//                    0, 1000,0 , 0, 5000, 0);
//            initializeQuery(Configuration.SPEED,Configuration.SUM,Configuration.PUNCTUATION,
//                    0, 0, 0, 1, 5000, 0);
//            initializeQuery(Configuration.SPEED,Configuration.SUM,Configuration.COUNTBASED,
//                    0, 0, 0, 0, 0, 1000000);

//            initializeQuery(Configuration.SPEED,Configuration.COUNT,Configuration.TUMBING,
//                    1000, 0, 0, 0, 0, 0);
//            initializeQuery(Configuration.SPEED,Configuration.COUNT,Configuration.SLIDING,
//                    4000, 2000, 0, 0, 0, 0);
//            initializeQuery(Configuration.SPEED,Configuration.COUNT,Configuration.SLIDING_UNEVEN,
//                    1000, 2000, 0, 0, 0, 0);
//            initializeQuery(Configuration.SPEED,Configuration.COUNT,Configuration.SESSION,
//                    0, 1000,0 , 0, 5000, 0);
//            initializeQuery(Configuration.SPEED,Configuration.COUNT,Configuration.PUNCTUATION,
//                    0, 0, 0, 1, 5000, 0);
//            initializeQuery(Configuration.SPEED,Configuration.COUNT,Configuration.COUNTBASED,
//                    0, 0, 0, 0, 0, 1000000);

//            initializeQuery(Configuration.SPEED,Configuration.QUANTILE,Configuration.TUMBING,
//                    1000, 0, 0, 0, 0, 0);
//            initializeQuery(Configuration.SPEED,Configuration.QUANTILE,Configuration.SLIDING,
//                    4000, 2000, 0, 0, 0, 0);
//            initializeQuery(Configuration.SPEED,Configuration.QUANTILE,Configuration.SLIDING_UNEVEN,
//                    1000, 2000, 0, 0, 0, 0);
//            initializeQuery(Configuration.SPEED,Configuration.QUANTILE,Configuration.SESSION,
//                    0, 1000,0 , 0, 5000, 0);
//            initializeQuery(Configuration.SPEED,Configuration.QUANTILE,Configuration.PUNCTUATION,
//                    0, 0, 0, 1, 5000, 0);
//            initializeQuery(Configuration.SPEED,Configuration.QUANTILE,Configuration.COUNTBASED,
//                    0, 0, 0, 0, 0, 1000000);

//            initializeQuery(Configuration.SPEED,Configuration.MEDIAN,Configuration.TUMBING,
//                    1000, 0, 0, 0, 0, 0);
//            initializeQuery(Configuration.SPEED,Configuration.MEDIAN,Configuration.SLIDING,
//                    4000, 2000, 0, 0, 0, 0);
//            initializeQuery(Configuration.SPEED,Configuration.MEDIAN,Configuration.SLIDING_UNEVEN,
//                    1000, 2000, 0, 0, 0, 0);
//            initializeQuery(Configuration.SPEED,Configuration.MEDIAN,Configuration.SESSION,
//                    0, 1000,0 , 0, 5000, 0);
//            initializeQuery(Configuration.SPEED,Configuration.MEDIAN,Configuration.PUNCTUATION,
//                    0, 0, 0, 1, 5000, 0);
//            initializeQuery(Configuration.SPEED,Configuration.MEDIAN,Configuration.COUNTBASED,
//                    0, 0, 0, 0, 0, 1000000);
//            initializeQuery(Configuration.SPEED,Configuration.MEDIAN,Configuration.TUMBING,
//                    1000, 0, 0, 0, 0, 0);

            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void initializeQuery(int Object, int function, int windowType, int range, int slide
            , int startPunctuation, int endPunctuation, int waterMark, int batchSize) {

        Query query = new Query();
        query.setQueryId(queryCounter++);
        query.setKey(Object);
        query.setFunction(function);
        query.setWindowType(windowType);
        query.setRange(range);
        query.setSlide(slide);
        query.setStartPunctuation(startPunctuation);
        query.setEndPunctuation(endPunctuation);
        query.setWaterMark(waterMark);
        query.setBatchSize(batchSize);
        query.setScenario(classifyQuery(query, conf));

        //to send the query
        queryQueue.offer(query);
        queryList.offer(query);

//        if(query.getScenario() == conf.DeCentralizedAggregation){
//            queryListDecentral.offer(query);
//            conf.setQueryNumberDecentral(conf.getQueryNumberDecentral()+1);
//        } else{
//            queryListCenral.offer(query);
//            conf.setQueryNumberCentral(conf.getQueryNumberCentral()+1);
//        }
    }

    private int classifyQuery(Query query, Configuration conf){
        return query.getFunction() < 5 ? conf.DeCentralizedAggregation : conf.CentralizedAggregation;
    }

}
