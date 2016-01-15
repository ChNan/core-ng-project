package util.time;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Dylan
 */
public class DateConvert {
    public static String toString(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }
}
