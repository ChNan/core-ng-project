package web.route;

import http.HttpMethod;
import util.Maps;
import web.ControllerHolder;

import java.util.Map;

/**
 * @author ChNan
 */
public class URLHandler {

    Map<HttpMethod, ControllerHolder> controllerHolderMap = Maps.newHashMap();

}
