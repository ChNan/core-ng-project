package bean;

import java.lang.reflect.Type;
import java.util.Objects;

/**
 * @author ChNan
 */
public class Key {

    private Type type;

    private String name;


    public Key(Type type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Key key = (Key) o;
        return Objects.equals(name, key.name) && Objects.equals(type, key.type);
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Key{" + "type=" + type + ", name='" + name + '\'' + '}';
    }
}
