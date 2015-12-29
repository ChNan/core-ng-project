package demo;

import http.annotation.GET;
import http.annotation.RequestPath;

/**
 * @author ChNan
 */
public class HelloWorldService {
    @GET
    @RequestPath("/helloWorld")
    public String helloWorld() {
        return "helloWorld";
    }
}
