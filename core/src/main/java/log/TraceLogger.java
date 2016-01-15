package log;

import ch.qos.logback.classic.spi.ILoggingEvent;

/**
 * @author Dylan
 */
public class TraceLogger implements ActionLogger {
    private ThreadLocal<ILoggingEventProcessor> logingEventProcessor = new ThreadLocal<>();
    private static TraceLogger INSTANCE = new TraceLogger();
    private String logFolder;

    public static TraceLogger get() {
        return INSTANCE;
    }

    public void process(ILoggingEvent event) {
        ILoggingEventProcessor processor = logingEventProcessor.get();
        if (null != processor) {
            processor.process(event);
        }
    }

    public void cleanUp() {
        ILoggingEventProcessor processor = logingEventProcessor.get();
        processor.actionLog.save(); // create action log
        processor.cleanup(); // finally close writer to end write trace log
        logingEventProcessor.remove();
    }

    public void initialize() {
        logingEventProcessor.set(new ILoggingEventProcessor(logFolder));
    }


    public void setLogFolder(final String logFolder) {
        this.logFolder = logFolder;
    }

    public void action(String action) {
        ActionLog actionLog = logingEventProcessor.get().actionLog;
        actionLog.action(action);
    }

    public void requestId(String requestId) {
        ActionLog actionLog = logingEventProcessor.get().actionLog;
        actionLog.requestId(requestId);
    }

    public void result(ActionResult result) {
        ActionLog actionLog = logingEventProcessor.get().actionLog;
        actionLog.result(result);
    }

    @Override
    public void logContext(final String key, final String value) {
        ActionLog actionLog = logingEventProcessor.get().actionLog;
        actionLog.logContext(key, value);
    }
}
