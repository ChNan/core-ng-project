package web;

import bean.BeanFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dylan
 */
public class ModuleContext {
    public final HttpServer httpServer;
    public final BeanFactory beanFactory;
    public final List<Runnable> startupHook;

    public ModuleContext() {
        httpServer = new HttpServer();
        beanFactory = new BeanFactory();
        startupHook = new ArrayList<>();
    }
}
