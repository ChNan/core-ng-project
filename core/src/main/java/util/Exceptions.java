package util;

import org.slf4j.helpers.MessageFormatter;

/**
 * @author Dylan
 */
public final class Exceptions {

    public static Error error(String pattern, Object... arguments) {
        return new Error(MessageFormatter.format(pattern, arguments).getMessage());
    }
}
