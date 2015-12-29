package web;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ChNan
 */
public class HttpServerHandler implements HttpHandler {

    public List<Controller> controllerContainers = new ArrayList<>();


    @Override
    public void handleRequest(final HttpServerExchange exchange) throws Exception {
        if (exchange.getRequestPath().contains("/order")) {
            System.out.println("Request url is order request ," + exchange.getRequestPath());
        } else {
            System.out.println("Request url is " + exchange.getRequestPath());
        }
        controllerContainers.forEach(c -> {
            try {
                c.execute(new Request() {
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
