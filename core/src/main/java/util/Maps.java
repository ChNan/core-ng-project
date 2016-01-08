package util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Dylan
 */
public class Maps {

    public static <K, V> Map<K, V> newHashMap() {
        return new HashMap<>();
    }
}
