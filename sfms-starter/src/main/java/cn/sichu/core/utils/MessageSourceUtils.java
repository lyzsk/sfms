package cn.sichu.core.utils;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * 国际化工具类
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public class MessageSourceUtils {

    private static final MessageSource MESSAGE_SOURCE = SpringUtil.getBean(MessageSource.class);
    private static final Object[] EMPTY_ARGS = {};

    private MessageSourceUtils() {
    }

    /**
     * 根据消息编码获取
     *
     * @param code 消息编码
     * @return 国际化后的消息
     */
    public static String getMessage(String code) {
        return getMessage(code, EMPTY_ARGS);
    }

    /**
     * 根据消息编码获取
     *
     * @param code 消息编码
     * @param args 参数
     * @return 国际化后的消息
     */
    public static String getMessage(String code, Object... args) {
        return getMessage(code, code, args);
    }

    /**
     * 根据消息编码获取
     *
     * @param code           消息编码
     * @param defaultMessage 默认消息
     * @return 国际化后的消息
     */
    public static String getMessage(String code, String defaultMessage) {
        return getMessage(code, defaultMessage, EMPTY_ARGS);
    }

    /**
     * 根据消息编码获取
     *
     * @param code           消息编码
     * @param defaultMessage 默认消息
     * @param args           参数
     * @return 国际化后的消息
     */
    public static String getMessage(String code, String defaultMessage, Object... args) {
        try {
            return MESSAGE_SOURCE.getMessage(code, args, LocaleContextHolder.getLocale());
        } catch (Exception e) {
            return defaultMessage;
        }
    }
}
