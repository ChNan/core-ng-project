//package demo;
//
//import app.product.service.ProductService;
//import core.framework.api.Module;
//import core.framework.impl.code.CodeBuilder;
//import javassist.CannotCompileException;
//import javassist.ClassPool;
//import javassist.CtClass;
//import javassist.CtConstructor;
//import javassist.CtField;
//import javassist.CtMethod;
//import javassist.NotFoundException;
//
//import java.io.IOException;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.util.concurrent.atomic.AtomicInteger;
//
///**
// * @author Dylan
// */
//public class CodeBuilderNgModule<T> extends Module {
//    Class service;
//
//    public T run() throws CannotCompileException, NotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
//        Class serviceInterface = ProductWebService.class;
//        Method method = serviceInterface.getDeclaredMethods()[0];
//
//        String className = service.getCanonicalName() + "$" + method.getName();
//
//        AtomicInteger INDEX = new AtomicInteger();
//        ClassPool classPool = ClassPool.getDefault();
//        CtClass classBuilder = classPool.makeClass(className + "$" + (INDEX.getAndIncrement()));
//        classBuilder.addField(CtField.make(new CodeBuilder().append("final {} delegate;", serviceInterface.getCanonicalName()).build(), classBuilder));
//
//        Class[] constructorParamClasses = new Class[]{serviceInterface};
//        CtClass[] params = new CtClass[constructorParamClasses.length];
//        for (int i = 0; i < constructorParamClasses.length; i++) {
//            Class<?> paramClass = constructorParamClasses[i];
//            params[i] = classPool.getCtClass(paramClass.getCanonicalName());
//        }
//        CtConstructor addConstructor = new CtConstructor(params, classBuilder);
//        addConstructor.setBody("this.delegate = $1;");
//        classBuilder.addConstructor(addConstructor);
//        classBuilder.addMethod(CtMethod.make("public core.framework.api.web.Response execute(core.framework.api.web.Request request) throws Exception {\n" +
//            "    java.lang.String id = (java.lang.String) request.pathParam(\"id\", java.lang.String.class);\n" +
//            "    app.product.api.ProductView response = delegate.get(id);" +
//            "    System.out.println(\"This is dylan test\");\n" +
//            "    return core.framework.api.web.Response.bean(response).status(core.framework.api.http.HTTPStatus.OK);\n" +
//            "}", classBuilder));
//        classBuilder.writeFile();
//        Class targetClass = classBuilder.toClass();
//        classBuilder.detach();
//        return (T) targetClass.getDeclaredConstructor(constructorParamClasses).newInstance(service);
//    }
//
//    @Override
//    protected void initialize() {
//        bind(ProductService.class);
//        service = bind(ProductWebServiceImpl.class).getClass();
//    }
//}
