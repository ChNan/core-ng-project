package web.request;

import http.ContentType;
import http.HttpMethod;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.form.FormData;
import io.undertow.util.HeaderMap;
import io.undertow.util.Headers;
import log.ActionLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Dylan
 */
public class Request {
    private HttpServerExchange exchange;
    private HttpMethod httpMethod;
    private String path;
    private String scheme;
    private String host;
    private int port;
    private String requestURL;
    private String clientIp;

    private String body;
    private ContentType contentType;
    private FormData formData;
    Logger logger = LoggerFactory.getLogger(Request.class);

    public Request() {

    }

    public Request(HttpServerExchange exchange, ActionLog actionLog) {
        this.exchange = exchange;

        this.httpMethod = HttpMethod.valueOf(exchange.getRequestMethod().toString());
        HeaderMap headers = exchange.getRequestHeaders();

        String xForwardFor = headers.getFirst(Headers.X_FORWARDED_FOR);
        String hostAddress = exchange.getSourceAddress().getAddress().getHostAddress();
        this.clientIp = findClientIp(xForwardFor, hostAddress);
        actionLog.logContext("clientIp", clientIp);

        String scheme = exchange.getRequestScheme();
        String xForwardProtocol = headers.getFirst(Headers.X_FORWARDED_PROTO);
        this.scheme = xForwardProtocol != null ? xForwardProtocol : scheme;
        actionLog.logContext("scheme", scheme);

        this.host = exchange.getHostName();
        actionLog.logContext("host", host);

        String xForwardPort = headers.getFirst(Headers.X_FORWARDED_PORT);
        int hostPort = exchange.getHostPort();
        this.port = findPort(xForwardPort, hostPort);
        actionLog.logContext("port", String.valueOf(port));

        this.path = exchange.getRequestPath();

        this.requestURL = createRequestURL(exchange);
        actionLog.logContext("requestURL", this.requestURL);

        if (this.httpMethod == HttpMethod.POST || this.httpMethod == HttpMethod.PUT) {
            String contentType = headers.getFirst(Headers.CONTENT_TYPE);
            this.contentType = contentType == null ? null : ContentType.parse(contentType);
        }

    }

    void parseBody(Request request) {

    }

    private String createRequestURL(HttpServerExchange exchange) {
        if (exchange.isHostIncludedInRequestURI()) {
            return exchange.getRequestURI();
        } else {
            StringBuilder requestURLBuilder = new StringBuilder();
            requestURLBuilder.append(this.scheme).append("://")
                .append(this.host).append(":");

            if (!(
                ("http".equals(this.scheme) && this.port == 80)
                    || ("https".equals(this.scheme) && this.port == 443)
            )) {
                requestURLBuilder.append(this.port)
                    .append(this.exchange.getRequestURI()).toString();
            }

            return requestURLBuilder.append(exchange.getRequestURI()).toString();
        }
    }

    String findClientIp(String xForwardFor, String hostAddress) {
        if (xForwardFor == null) return hostAddress;

        int index = xForwardFor.indexOf(',');
        if (index > 0) return xForwardFor.substring(0, index);

        return xForwardFor;
    }

    int findPort(String xForwardPort, int hostPort) {
        if (xForwardPort == null) return hostPort;

        int index = xForwardPort.indexOf(',');
        if (index > 0) {
            return Integer.valueOf(xForwardPort.substring(0, index));
        } else {
            return Integer.valueOf(xForwardPort);
        }
    }


}
