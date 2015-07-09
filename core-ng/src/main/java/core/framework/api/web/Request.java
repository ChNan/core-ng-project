package core.framework.api.web;

import core.framework.api.http.HTTPMethod;
import core.framework.api.web.exception.ValidationException;

import java.lang.reflect.Type;
import java.util.Optional;

/**
 * @author neo
 */
public interface Request {
    String requestURL();  //original request url without decoding

    String scheme();

    String host();

    String path();  //requestPath decoded by server

    HTTPMethod method();

    Optional<String> header(String name);

    <T> T pathParam(String name, Class<T> valueClass);

    Optional<String> queryParam(String name);

    Optional<String> formParam(String name);

    <T> T bean(Type instanceType) throws ValidationException;

    String clientIP();

    Optional<String> cookie(CookieSpec spec);

    Session session();

    default String pathParam(String name) {
        return pathParam(name, String.class);
    }
}