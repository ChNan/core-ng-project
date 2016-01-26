package web;

import http.HttpMethod;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import log.TraceLogger;
import web.request.Request;
import web.route.Route;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ChNan
 */
public class HttpServerHandler implements HttpHandler {
    public List<Controller> controllerContainers = new ArrayList<>();

    public Route route = new Route();
    private TraceLogger traceLogger;

    public HttpServerHandler(TraceLogger traceLogger) {
        this.traceLogger = traceLogger;
    }

    @Override
    public void handleRequest(final HttpServerExchange exchange) throws Exception {
        ControllerHolder controllerHolder = route.get(exchange.getRequestPath(), HttpMethod.valueOf(exchange.getRequestMethod().toString()));
        controllerHolder.controller.execute(new Request(exchange, traceLogger));
    }
}
