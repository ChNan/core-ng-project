import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class JavassistGenerator {

    public static void main(String[] args) throws NotFoundException, CannotCompileException, IOException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {


        // 创建类
        ClassPool pool = ClassPool.getDefault();

        CtClass ccClass = pool.makeClass("Point" );
        String bodyString = "{System.out.println(\"Call to method \");}" ;
        //为新创建的类新加一个方法execute，无任何参数
        CtMethod n1 = CtNewMethod.make(CtClass.voidType, "execute" ,  null ,  null ,
            bodyString, ccClass);
        ccClass.addMethod(n1);
        //新加第二个方法
        bodyString = "public Integer getNumber(Integer num,String name);" ;
        CtMethod n2 = CtNewMethod.make(bodyString, ccClass);//直接创建一个方法，带有一个int的参数和返回值
        n2.setBody("{System.out.println(\"Point Call to method \");$2=\"22\";return $1;}" );
        ccClass.addMethod(n2);
        ccClass.writeFile();


        CtClass cls = pool.makeClass("cn.ibm.com.TestClass");

        // 添加私有成员name及其getter、setter方法
        CtField param = new CtField(pool.get("java.lang.String"), "name", cls);
        param.setModifiers(Modifier.PRIVATE);
        cls.addMethod(CtNewMethod.setter("setName", param));
        cls.addMethod(CtNewMethod.getter("getName", param));
        cls.addField(param, CtField.Initializer.constant(""));

        // 添加无参的构造体
        CtConstructor cons = new CtConstructor(new CtClass[]{}, cls);
        cons.setBody("{name = \"Brant\";}");
        cls.addConstructor(cons);

        // 添加有参的构造体
        cons = new CtConstructor(new CtClass[]{pool.get("java.lang.String")}, cls);
        cons.setBody("{$0.name = $1;}");
        cls.addConstructor(cons);

        cls.writeFile();

        // 打印创建类的类名
        System.out.println(cls.toClass());

        // 通过反射创建无参的实例，并调用getName方法
        Object o = Class.forName("cn.ibm.com.TestClass").newInstance();
        Method getter = o.getClass().getMethod("getName");
        System.out.println(getter.invoke(o));

        // 调用其setName方法
        Method setter = o.getClass().getMethod("setName", new Class[]{String.class});
        setter.invoke(o, "Adam");
        System.out.println(getter.invoke(o));

        // 通过反射创建有参的实例，并调用getName方法
        o = Class.forName("cn.ibm.com.TestClass").getConstructor(String.class).newInstance("Liu Jian");
        getter = o.getClass().getMethod("getName");
        System.out.println(getter.invoke(o));
    }

}