package config;

import context.Context;
import http.annotation.RequestPath;
import web.Controller;
import context.ModuleContext;
import web.ServiceControllerBuilder;

import java.lang.reflect.Method;

/**
 * @author ChNan
 */
public class APIConfig extends Context {

    public APIConfig(ModuleContext context) {
        this.context = context;
    }

    public <T> void service(Class<T> interfaceClass, T interfaceImpl) {
        Method[] interfaceMethods = interfaceClass.getDeclaredMethods();
        for (Method interfaceMethod : interfaceMethods) {
            RequestPath requestPath = interfaceMethod.getAnnotation(RequestPath.class);
            String path = requestPath.value();
            Controller controller = (Controller) new ServiceControllerBuilder<>(interfaceClass,
                interfaceImpl, interfaceMethod).build();
            context.httpServer.handler.controllerContainers.add(controller);
        }
    }
}
