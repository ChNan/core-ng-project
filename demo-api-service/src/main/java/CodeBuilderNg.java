import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Dylan
 */
public class CodeBuilderNg {
    public static void main(String[] args) throws CannotCompileException {
        String className = ProductW
        AtomicInteger INDEX = new AtomicInteger();
        ClassPool classPool = ClassPool.getDefault();
        CtClass classBuilder = classPool.makeClass(className + "$" + (INDEX.getAndIncrement()));
        classBuilder.addField(CtField.make("dylanName", classBuilder));
        classBuilder.addMethod(CtMethod.make("public core.framework.api.web.Response execute(core.framework.api.web.Request request) throws Exception {\n" +
            "    java.lang.String id = (java.lang.String) request.pathParam(\"id\", java.lang.String.class);\n" +
            "    app.product.api.ProductView response = delegate.get(id);\n" +
            "    return core.framework.api.web.Response.bean(response).status(core.framework.api.http.HTTPStatus.OK);\n" +
            "}",));
    }
}
