package log;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;

/**
 * @author Dylan
 */
public class TraceAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {


    @Override
    protected void append(final ILoggingEvent event) {
        TraceLogger.get().process(event);
    }

    @Override
    public void start() {
        super.start();
        FilterMessagePattern.get().setContext(getContext());
        FilterMessagePattern.get().start();
    }

    @Override
    public void stop() {
        super.stop();
        FilterMessagePattern.get().stop();
    }

    public void setLogFolder(String logFolder) {
        TraceLogger.get().setLogFolder(logFolder);
    }
}
