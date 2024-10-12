package cn.sichu.core.utils;

import cn.hutool.core.text.CharSequenceUtil;

import java.util.function.Function;

/**
 * 字符串工具类
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public class StrUtils {

    private StrUtils() {
    }

    /**
     * 如果字符串是{@code null}或者&quot;&quot;或者空白，则返回指定默认字符串，否则针对字符串处理后返回
     *
     * @param str          要转换的字符串
     * @param defaultValue 默认值
     * @param mapper       针对字符串的转换方法
     * @return 转换后的字符串或指定的默认字符串
     * @since 2.0.1
     */
    public static <T> T blankToDefault(CharSequence str, T defaultValue,
        Function<String, T> mapper) {
        return CharSequenceUtil.isBlank(str) ? defaultValue : mapper.apply(str.toString());
    }
}
