package util.time;

import java.text.MessageFormat;

/**
 * @author Dylan
 */
public final class StopWatch {
    private long start;

    private int i = 0;

    private long elapsedTime = 0L;

    public StopWatch() {
        reset();
    }

    public void reset() {
        start = System.currentTimeMillis();

    }

    public long elapsedTime() {
        long end = System.currentTimeMillis();
        elapsedTime = end - start;
        reset(); // for call next time
        return elapsedTime;
    }

    public String elapsedMsg() {
        elapsedTime();
        return MessageFormat.format("watch {0}, elapsed time is {1} ms", i++, elapsedTime);
    }
}
