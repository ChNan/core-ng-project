package log;


import util.clazz.ClassUtils;

import java.util.Date;

/**
 * @author Dylan
 */
public class Test {
    public static void main(String[] args) throws NoSuchMethodException {
        System.out.println(String.format("$s%2$tY%2$tm$td$tM$tS$tL", "Test", new Date()));
        System.out.println(ClassUtils.getSimpleClassName(new Test()));
        System.out.println(new Test().getClass().getMethod("main", String[].class));
    }


}
