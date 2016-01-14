package web.route;

import http.HttpMethod;
import util.Exceptions;
import util.Maps;
import web.ControllerHolder;

import java.util.Map;

/**
 * @author ChNan
 */
public class URLHandler {

    final String path;

    public URLHandler(String path) {
        this.path = path;
    }

    Map<HttpMethod, ControllerHolder> controllerMethodMap = Maps.newHashMap();


    public void put(HttpMethod httpMethod, ControllerHolder controllerHolder) {
        ControllerHolder previous = controllerMethodMap.putIfAbsent(httpMethod, controllerHolder);
        if (previous != null) {
            throw Exceptions.error("Controller conflict ,exist {} already", previous);
        }
    }

    public ControllerHolder get(HttpMethod httpMethod) {
        return controllerMethodMap.get(httpMethod);
    }
}