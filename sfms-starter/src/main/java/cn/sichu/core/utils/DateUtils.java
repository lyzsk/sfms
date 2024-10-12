package cn.sichu.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author sichu huang
 * @date 2024/10/12
 **/
public class DateUtils {
    private static final SimpleDateFormat YYYY_MM_DD_HH_MM_SS_SSS =
        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public static Date parseMillisecond(Date date) throws ParseException {
        return parseMillisecond(formatMillisecond(date));
    }

    public static String formatMillisecond(Date date) {
        return YYYY_MM_DD_HH_MM_SS_SSS.format(date);
    }

    public static Date parseMillisecond(String dateStr) throws ParseException {
        return YYYY_MM_DD_HH_MM_SS_SSS.parse(dateStr);
    }
}
