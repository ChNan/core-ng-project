package web.request;

import http.HttpMethod;
import io.undertow.server.HttpServerExchange;

/**
 * @author Dylan
 */
public class RequestImpl {
    public RequestImpl(HttpServerExchange exchange) {
        this.exchange = exchange;
    }

    private final HttpServerExchange exchange;

    private HttpMethod httpMethod;

    private int port;

    private String scheme;

    private String uri;

    private String body;



}
