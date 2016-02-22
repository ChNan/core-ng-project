package app.web.helloworld;

import app.web.TestModel;
import core.framework.api.web.Request;
import core.framework.api.web.Response;

/**
 * @author Dylan
 */
public class HelloWorldController {

    public Response getHelloPage(Request request) throws Exception {
        return Response.html("/static/home.html", new TestModel());
    }


}
