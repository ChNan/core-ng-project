package bean;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Dylan
 */
public class BeanFactory {
    Map<Key, Object> beans = new HashMap<>();

    public <T> T create(Class<T> targetClass) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        Constructor<T> targetConstructor = targetClass.getDeclaredConstructor();
        Object[] targetParams = new Object[]{};
        return targetClass.cast(targetConstructor.newInstance(targetParams));
    }

    public <T> T bind(Type type, String name, T targetInstance) {
        beans.put(new Key(type, name), targetInstance);
        return targetInstance;
    }

}
