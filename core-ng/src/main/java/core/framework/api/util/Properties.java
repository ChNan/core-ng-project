package core.framework.api.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.UncheckedIOException;
import java.util.Map;
import java.util.Optional;

/**
 * @author neo
 */
public class Properties {
    final Map<String, String> properties = Maps.newHashMap();

    public void load(String path) {
        try {
            java.util.Properties properties = new java.util.Properties();
            properties.load(new StringReader(ClasspathResources.text(path)));
            properties.forEach((key, value) -> {
                String previous = this.properties.putIfAbsent((String) key, (String) value);
                if (previous != null)
                    throw Exceptions.error("property already exists, key={}, previous={}, current={}", key, previous, value);
            });
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public Optional<String> get(String key) {
        String value = properties.get(key);
        if (!Strings.empty(value)) {
            return Optional.of(value);
        }
        return Optional.empty();
    }
}