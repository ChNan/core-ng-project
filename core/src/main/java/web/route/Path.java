package web.route;

import util.Exceptions;
import util.Maps;

import java.util.Map;

/**
 * @author Dylan
 */
public class Path {
    Map<String, URLHandler> urlHandlerMap = Maps.newHashMap();

    public URLHandler register(String path) {
        URLHandler urlHandler = new URLHandler(path);
        if (null != urlHandlerMap.putIfAbsent(path, urlHandler))
            throw Exceptions.error("Path {} has register", path);
        return urlHandler;
    }

    public URLHandler find(String path) {
        return urlHandlerMap.get(path);
    }

}
