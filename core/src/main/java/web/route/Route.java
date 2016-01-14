package web.route;

import http.HttpMethod;
import web.ControllerHolder;

/**
 * @author ChNan
 */
public class Route {

    private URLHandler urlHandler;

    private Path path = new Path();

    public void add(HttpMethod httpMethod, String requestPath, ControllerHolder controllerHolder) {
        urlHandler = path.register(requestPath);
        urlHandler.put(httpMethod, controllerHolder);
    }

    public ControllerHolder get(String requestPath, HttpMethod httpMethod) {
        URLHandler urlHandler = path.find(requestPath);
        return urlHandler.get(httpMethod);
    }


}
