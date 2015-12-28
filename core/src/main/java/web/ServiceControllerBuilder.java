package web;

import java.lang.reflect.Method;

/**
 * @author Dylan
 */
public class ServiceControllerBuilder<T> {

    private Class<T> serviceInterface;

    private T serviceImpl;

    private Method targetMethod;

    public ServiceControllerBuilder(Class<T> serviceInterface, T serviceImpl, Method targetMethod) {
        this.serviceInterface = serviceInterface;
        this.serviceImpl = serviceImpl;
        this.targetMethod = targetMethod;
    }

    public void build() {

    }
}
