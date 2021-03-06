package De.Hpi.Disco.Source.input;

import De.Hpi.Disco.Source.utility.Event;
import org.zeromq.ZMQ;

import java.util.Random;
import java.util.function.Function;

/**
 * Uses the random function to sleep. This causes the event time to progress "normally". The sleep times are
 * deterministic under the same `rand` condition but the sleep is not.
 */
public class SleepEventGenerator implements EventGenerator {
    private final int streamId;
    private final InputStreamConfig config;
    private final Random rand;

    public SleepEventGenerator(int streamId, InputStreamConfig config) {
        this.streamId = streamId;
        this.config = config;
        this.rand = new Random();
    }

    @Override
    public final long generateAndSendEvents(ZMQ.Socket eventSender) throws Exception {
        long lastEventTimestamp = 0;
        final long startTime = config.startTimestamp;
        final Function<Long, Long> eventGenerator = config.generatorFunction;
        final int minSleepTime = config.minWaitTimeMillis;
        final int maxSleepTime = config.maxWaitTimeMillis;

        long sendSecondEnd = System.currentTimeMillis() + 1000;
        long lastSendCount = 0;

        for (int i = 0; i < config.numEventsToSend; i++) {
            this.doSleep(minSleepTime, maxSleepTime);

            final long realTimestamp = System.currentTimeMillis();
            final long eventTimestamp = realTimestamp - startTime;
//            final Double eventValue = eventGenerator.apply(realTimestamp);
            final Double eventValue = Double.valueOf(realTimestamp);
            final Event event = new Event(eventValue, eventTimestamp, this.streamId);
            final String msg = event.asString();
            eventSender.send(msg);
            System.out.println(event.getValue());
            System.out.println(event.getTimestamp());
            System.out.println(event.getKey());
            System.out.println(event.getStreamId());

            lastEventTimestamp = eventTimestamp;

            if (System.currentTimeMillis() > sendSecondEnd) {
                System.out.println("Sent " + (i - lastSendCount) + " events in last second.");
                lastSendCount = i;
                sendSecondEnd += 1000;
            }
        }

        return lastEventTimestamp;
    }

    protected void doSleep(int minSleep, int maxSleep) throws InterruptedException {
        int sleepTime = rand.nextInt((maxSleep - minSleep) + 1) + minSleep;
        Thread.sleep(sleepTime);
    }
}

