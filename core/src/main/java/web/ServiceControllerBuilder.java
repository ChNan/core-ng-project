package web;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.helpers.MessageFormatter;

/**
 * @author Dylan
 */
public class ServiceControllerBuilder<T> {

    private final Class<T> serviceInterface;

    private final T serviceImpl;

    private final Method targetMethod;
    ClassPool classPool;
    CtClass conCreateControllerClass;

    public ServiceControllerBuilder(Class<T> serviceInterface, T serviceImpl, Method targetMethod) {
        this.serviceInterface = serviceInterface;
        this.serviceImpl = serviceImpl;
        this.targetMethod = targetMethod;
    }

    @SuppressWarnings("unchecked")
    public T build(String conCreateControllerName) throws NotFoundException, CannotCompileException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        AtomicInteger INDEX = new AtomicInteger();
        classPool = ClassPool.getDefault();
        conCreateControllerClass = classPool.makeClass(conCreateControllerName + "$" + targetMethod.getName() + "$" + INDEX.getAndDecrement());
        conCreateControllerClass.addField(CtField.make(MessageFormatter.format("final {} delegate;",
                serviceInterface.getCanonicalName()).getMessage(),
            conCreateControllerClass));
        conCreateControllerClass.addInterface(classPool.getCtClass(Controller.class.getCanonicalName()));
        CtConstructor constructor = new CtConstructor(null, conCreateControllerClass);
        constructor.setBody(";");
        conCreateControllerClass.addConstructor(constructor);
        addConstructor(new Class[]{serviceInterface}, "this.delegate = $1;");

        conCreateControllerClass.addMethod(CtMethod.make(
            MessageFormatter.format("public {} execute({} request) throws Exception {",
                String.class.getCanonicalName(), Request.class.getCanonicalName()
            ).getMessage()
                + "delegate." + targetMethod.getName() + "();"
                +
                " return \"222\";}",
            conCreateControllerClass));

        @SuppressWarnings("unchecked")
        Class<T> cs = conCreateControllerClass.toClass();
        conCreateControllerClass.detach();
        //找到特定的构造函数cs.getDeclaredConstructor(new Class[]{serviceInterface})
        //把 具体的实现类当做参数传进来，最终实例化出一个Controller实例
        return (T) cs.getDeclaredConstructor(new Class[]{serviceInterface}).newInstance(serviceImpl);

    }

    public void addConstructor(Class[] constructorParamClasses, String body) throws NotFoundException, CannotCompileException {

        CtClass[] params = new CtClass[constructorParamClasses.length];
        for (int i = 0; i < constructorParamClasses.length; i++) {
            Class<?> paramClass = constructorParamClasses[i];
            params[i] = classPool.getCtClass(paramClass.getCanonicalName());
        }
        CtConstructor constructor = new CtConstructor(params, conCreateControllerClass);
        constructor.setBody(body);
        conCreateControllerClass.addConstructor(constructor);
    }
}
