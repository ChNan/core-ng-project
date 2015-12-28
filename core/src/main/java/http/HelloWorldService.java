package http;

import http.annotation.GET;
import http.annotation.RequestPath;

/**
 * @author Dylan
 */
public class HelloWorldService {
    @GET
    @RequestPath("/helloWorld")
    public String helloWorld() {
        return "helloWorld";
    }
}
