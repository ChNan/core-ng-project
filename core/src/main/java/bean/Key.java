package bean;

import java.lang.reflect.Type;

/**
 * @author Dylan
 */
public class Key {

    private Type type;

    private String name;


    public Key(Type type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Key{" + "type=" + type + ", name='" + name + '\'' + '}';
    }
}
