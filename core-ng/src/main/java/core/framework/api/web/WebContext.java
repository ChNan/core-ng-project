package core.framework.api.web;

/**
 * @author neo
 */
public interface WebContext {
    <T> T get(String key);

    <T> void put(String key, T value);

    Request request();
}
