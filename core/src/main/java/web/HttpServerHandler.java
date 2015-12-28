package web;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;

/**
 * @author Dylan
 */
public class HttpServerHandler implements HttpHandler {
    @Override
    public void handleRequest(final HttpServerExchange exchange) throws Exception {
        if (exchange.getRequestPath().contains("/order")) {
            System.out.println("Request url is order request ," + exchange.getRequestPath());
        }else{
            System.out.println("Request url is " + exchange.getRequestPath());
        }
    }
}
