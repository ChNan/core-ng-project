import bean.BeanFactory;
import http.annotation.RequestPath;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import web.HttpServerIOHandler;
import web.ServiceControllerBuilder;
import web.demo.OrderWebService;
import web.demo.OrderWebServiceImpl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Dylan
 */
public class Run {
    public static <T> void main(String[] args) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        BeanFactory beanFactory = new BeanFactory();
        Undertow server = Undertow.builder()
            .addHttpListener(8085, "localhost")
            .setHandler(new HttpHandler() {
                @Override
                public void handleRequest(HttpServerExchange exchange) throws Exception {
                    exchange.dispatch(new HttpServerIOHandler());
                }
            }).build();
        server.start();
        Map<String, Object> context = new HashMap<>();

        Map<String, Object> beans = new HashMap<>();
        Class interfaceClass = OrderWebService.class;
        String serviceImplName = OrderWebServiceImpl.class.getCanonicalName();
        beans.put(serviceImplName, beanFactory.create(OrderWebServiceImpl.class));

        Method[] interfaceMethods = interfaceClass.getDeclaredMethods();
        for (Method interfaceMethod : interfaceMethods) {
            RequestPath requestPath = interfaceMethod.getAnnotation(RequestPath.class);
            String path = requestPath.value();
            new ServiceControllerBuilder<>(interfaceClass, beans.get(serviceImplName), interfaceMethod);
        }


    }
}
