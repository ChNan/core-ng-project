package app.web.helloworld;

import core.framework.api.web.Request;
import core.framework.api.web.Response;

/**
 * @author Dylan
 */
public class HelloWorldAjaxController {

    public Response submitHello(Request request) throws Exception {
        HelloWorld helloWorld = new HelloWorld();
        helloWorld.name = "Dylan Test";
        return Response.bean(helloWorld);
    }
}
