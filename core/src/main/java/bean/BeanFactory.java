package bean;

import org.slf4j.helpers.MessageFormatter;

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

    public <T> T bind(Class<T> targetClass) {
        try {
            Constructor<T> targetConstructor = targetClass.getDeclaredConstructor();
            Object[] targetParams = new Object[]{};
            T targetInstance = null;
            if (targetConstructor != null) {
                targetInstance = targetClass.cast(targetConstructor.newInstance(targetParams));
            }
            beans.put(new Key(targetClass, targetClass.getCanonicalName()), targetInstance);
            return targetInstance;
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T bean(Class<T> targetClass) {
        return bean(targetClass, targetClass.getCanonicalName());
    }

    @SuppressWarnings("unchecked")
    private <T> T bean(Type type, String name) {
        T targetBean = (T) beans.get(new Key(type, name));
        if (null == targetBean) throw new Error(MessageFormatter.format("Can not find bean, type={} name ={}", type, name).getMessage());
        return targetBean;
    }
}
