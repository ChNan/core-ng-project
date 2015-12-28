import web.HttpServerIOHandler;
import web.demo.OrderWebServiceImpl;
import java.util.HashMap;
import java.util.Map;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;

/**
 * @author Dylan
 */
public class Run {
    public static void main(String[] args) {
        Undertow server = Undertow.builder()
            .addHttpListener(8085, "localhost")
            .setHandler(new HttpHandler() {
                @Override
                public void handleRequest(HttpServerExchange exchange) throws Exception {
                    exchange.dispatch(new HttpServerIOHandler());
                }
            }).build();
        server.start();
        Map<String, Object> context = new HashMap<>();

        Map<String, Object> beans = new HashMap<>();
        beans.put(OrderWebServiceImpl.class.getCanonicalName(), OrderWebServiceImpl.class);
    }
}
