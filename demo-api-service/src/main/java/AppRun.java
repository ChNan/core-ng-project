import http.HelloWorldService;
import http.annotation.RequestPath;

import java.lang.reflect.Method;

public class AppRun {
    public static void main(String[] args) {

        Method[] callMethods = HelloWorldService.class.getMethods();
        for (Method callMethod : callMethods) {
            String requestPath = callMethod.getDeclaredAnnotation(RequestPath.class).value();

        }
//        new http.HelloWorldWebServiceImpl().helloWorld();
    }
}