package cn.sichu.utils;

/**
 * @author sichu huang
 * @date 2024/10/06
 **/
public class StringUtil {
    private static final String NULLSTR = "";

    public static boolean isNull(Object object) {
        return object == null;
    }

    public static boolean isNotNull(Object object) {
        return !isNull(object);
    }

    public static boolean isEmpty(String str) {
        return isNull(str) || NULLSTR.equals(str.trim());
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static String trim(String str) {
        return (str == null ? "" : str.trim());
    }
}
