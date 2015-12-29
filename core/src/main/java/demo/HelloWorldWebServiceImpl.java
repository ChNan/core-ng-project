package demo;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

/**
* @author Dylan
*/
public class HelloWorldWebServiceImpl {

    //todo how to implement a web service
    // 1: Get client call
    // 2: Parse call, and do action
    // 2.1 context, save bean

    // 3: Return response
    public String helloWorld() {
        Undertow server = Undertow.builder()
            .addHttpListener(8085, "localhost")
            .setHandler(new HttpHandler() {
                @Override
                public void handleRequest(HttpServerExchange exchange) throws Exception {
                    exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
                    exchange.getResponseSender().send("Hello World");
                }
            }).build();
        server.start();
        return "helloworld";
    }
}
