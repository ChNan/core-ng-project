package web;

import java.lang.reflect.Method;

/**
 * @author Dylan
 */
public class ControllerHolder {

    public Controller controller;
    public Method targetMethod;

    public String controllerInfo;

    public boolean skiInterceptor;

    public ControllerHolder(Controller controller) {
        this(controller, null, false);
    }

    public ControllerHolder(Controller controller, Method targetMethod) {
        this(controller, targetMethod, false);
    }


    public ControllerHolder(Controller controller, Method targetMethod, boolean skiInterceptor) {
        this.controller = controller;
        this.targetMethod = targetMethod;
        this.skiInterceptor = skiInterceptor;
    }
}
