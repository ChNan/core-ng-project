package http;

import io.undertow.Undertow;
import web.HttpServerHandler;
import web.HttpServerIOHandler;

/**
 * @author ChNan
 */
public class HttpServer {
    public final HttpServerHandler handler;

    public HttpServer() {
        handler = new HttpServerHandler();
    }

    public void start() {
        Undertow server = Undertow.builder()
            .addHttpListener(8085, "localhost")
            .setHandler(exchange -> exchange.dispatch(new HttpServerIOHandler(handler))).build();
        server.start();
    }
}
