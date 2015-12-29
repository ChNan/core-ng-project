package config;

import http.annotation.RequestPath;
import web.Controller;
import web.ModuleContext;
import web.ServiceControllerBuilder;

import java.lang.reflect.Method;

/**
 * @author Dylan
 */
public class APIConfig {
    private ModuleContext context;

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
            context.httpServer.handler.controllerList.add(controller);
        }
    }
}
