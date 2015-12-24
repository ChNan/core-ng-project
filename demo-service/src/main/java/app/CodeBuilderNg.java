package app;

import core.framework.impl.code.CodeBuilder;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Dylan
 */
public class CodeBuilderNg {
    public static void main(String[] args) throws CannotCompileException, NotFoundException {
        Class serviceInterface = ProductWebService.class;
        Method method = serviceInterface.getDeclaredMethods()[0];
        Class service = ProductWebServiceImpl.class;
        String className = service.getCanonicalName() + "$" + method.getName();

        AtomicInteger INDEX = new AtomicInteger();
        ClassPool classPool = ClassPool.getDefault();
        CtClass classBuilder = classPool.makeClass(className + "$" + (INDEX.getAndIncrement()));
        classBuilder.addField(CtField.make(new CodeBuilder().append("final {} delegate;", serviceInterface.getCanonicalName()).build(), classBuilder));

        CtClass[] params = new CtClass[new Class[]{serviceInterface}.length];
        for (int i = 0; i < constructorParamClasses.length; i++) {
            Class<?> paramClass = constructorParamClasses[i];
            params[i] = classPool.getCtClass(paramClass.getCanonicalName());
        }
        CtConstructor constructor = new CtConstructor(params, classBuilder);
        constructor.setBody("this.delegate = $1;");
        classBuilder.addConstructor(constructor);
        classBuilder.addMethod(CtMethod.make("public core.framework.api.web.Response execute(core.framework.api.web.Request request) throws Exception {\n" +
            "    java.lang.String id = (java.lang.String) request.pathParam(\"id\", java.lang.String.class);\n" +
            "    app.product.api.ProductView response = delegate.get(id);\n" +
            "    return core.framework.api.web.Response.bean(response).status(core.framework.api.http.HTTPStatus.OK);\n" +
            "}", classBuilder));

        Class targetClass = classBuilder.toClass();
        classBuilder.detach();
        targetClass.getDeclaredConstructor(constructorParamClasses).newInstance(constructorParams);
    }
}
