package web;

import util.Charsets;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.form.FormParserFactory;


/**
 * @author Dylan
 */
public class HttpServerIOHandler implements HttpHandler {
    private FormParserFactory formParserFactory;

    public HttpServerIOHandler() {
        FormParserFactory.Builder formParserFactoryBuilder = FormParserFactory.builder();
        formParserFactoryBuilder.setDefaultCharset(Charsets.UTF_8.name());
    }

    @Override
    public void handleRequest(final HttpServerExchange exchange) throws Exception {
        exchange.dispatch(new HttpServerHandler());
    }
}
