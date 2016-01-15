package util.clazz;

/**
 * @author Dylan
 */
public class ClassUtils {
    /**
     * like full class is : com.iliangfeng.core.log.TraceLogger
     * generic class name is TraceLogger
     */
    public static String getSimpleClassName(Object o) {
        String fullClassName = o.getClass().getName();
        if (fullClassName.contains("$$EnhancerBySpringCGLIB")) {
            fullClassName = o.getClass().getSuperclass().getName();
        }
        int lastDotIndex = fullClassName.lastIndexOf('.');
        if (lastDotIndex > -1) {
            return fullClassName.substring(lastDotIndex + 1);
        }
        return fullClassName;
    }
}
