package util;

import org.slf4j.helpers.MessageFormatter;

/**
 * @author Dylan
 */
public class Strings {

    public static String format(String pattern, Object... arguments) {
        return MessageFormatter.arrayFormat(pattern, arguments).getMessage();
    }
}
