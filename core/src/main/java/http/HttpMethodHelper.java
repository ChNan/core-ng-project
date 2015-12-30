package http;

import http.annotation.DELETE;
import http.annotation.GET;
import http.annotation.POST;
import http.annotation.PUT;
import util.Strings;

import java.lang.reflect.Method;

/**
 * @author ChNan
 */
public class HttpMethodHelper {
    public static HttpMethod method(Method method) {
        if (method.isAnnotationPresent(GET.class)) return HttpMethod.GET;
        if (method.isAnnotationPresent(PUT.class)) return HttpMethod.PUT;
        if (method.isAnnotationPresent(POST.class)) return HttpMethod.POST;
        if (method.isAnnotationPresent(DELETE.class)) return HttpMethod.DELETE;
        throw new Error(Strings.format("Unsupported method, method = {}", method));
    }


}
