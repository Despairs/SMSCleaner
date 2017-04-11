package despairs.smscleaner;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by EKovtunenko on 11.04.2017.
 */

public class DateUtils {

    public static String toString(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }
}
