package context;

import bean.BeanFactory;
import http.HttpServer;
import log.TraceLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ChNan
 */
public class ModuleContext {
    public final HttpServer httpServer;
    public final BeanFactory beanFactory;
    public final List<Runnable> startupHook;
    public final TraceLogger traceLogger;

    public ModuleContext() {
        httpServer = new HttpServer(TraceLogger.get());
        beanFactory = new BeanFactory();
        startupHook = new ArrayList<>();
        traceLogger = TraceLogger.get();
    }
}
