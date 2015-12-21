import http.GET;
import http.RequestPath;

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
