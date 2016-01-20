package http;

import io.undertow.Undertow;
import log.TraceLogger;
import web.HttpServerHandler;
import web.HttpServerIOHandler;

/**
 * @author ChNan
 */
public class HttpServer {
    static {
        // make undertow to use slf4j
        System.setProperty("org.jboss.logging.provider", "slf4j");
    }

    public final HttpServerHandler handler;

    private TraceLogger traceLogger;
    public HttpServer(TraceLogger traceLogger) {
        handler = new HttpServerHandler(traceLogger);
    }

    public void start() {
        Undertow server = Undertow.builder()
            .addHttpListener(8085, "localhost")
            .setHandler(exchange -> exchange.dispatch(new HttpServerIOHandler(handler)))
            .build();
        server.start();
    }
}
