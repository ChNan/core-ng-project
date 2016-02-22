package app;

import app.web.helloworld.HelloWorldAjaxController;
import app.web.helloworld.HelloWorldController;
import app.web.TestModel;
import core.framework.api.Module;

import java.util.Optional;

/**
 * @author Dylan
 */
public class DemoSiteModule extends Module {
    @Override
    protected void initialize() {
        // site static
        site().staticContent("/static");
        site().staticContent("/favicon.ico");
        site().staticContent("/robots.txt");

        // site message
        site().message().language(r -> Optional.of("zh"));

        site().message().loadProperties("messages/main.properties");
        site().message().loadProperties("messages/main_zh.properties");


        // get

        HelloWorldController hello = bind(HelloWorldController.class);

        site().template("/static/home.html", TestModel.class);
        route().get("/hello", hello::getHelloPage);

        // post
        HelloWorldAjaxController helloWorldAjax = bind(HelloWorldAjaxController.class);

        route().post("/submitHelloWorld", helloWorldAjax::submitHello);
    }
}
