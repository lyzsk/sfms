package cn.sichu.core.utils;

import cn.sichu.core.constant.StringConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Consumer;

/**
 * 异常工具类
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public class ExceptionUtils {
    private static final Logger log = LoggerFactory.getLogger(ExceptionUtils.class);

    private ExceptionUtils() {
    }

    /**
     * 打印线程异常信息
     *
     * @param runnable  线程执行内容
     * @param throwable 异常
     */
    public static void printException(Runnable runnable, Throwable throwable) {
        if (null == throwable && runnable instanceof Future<?> future) {
            try {
                if (future.isDone()) {
                    future.get();
                }
            } catch (CancellationException e) {
                throwable = e;
            } catch (ExecutionException e) {
                throwable = e.getCause();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        if (null != throwable) {
            log.error(throwable.getMessage(), throwable);
        }
    }

    /**
     * 如果有异常，返回 null
     *
     * @param exSupplier 可能会出现异常的方法执行
     * @param <T>        /
     * @return /
     */
    public static <T> T exToNull(ExSupplier<T> exSupplier) {
        return exToDefault(exSupplier, null);
    }

    /**
     * 如果有异常，执行异常处理
     *
     * @param supplier   可能会出现异常的方法执行
     * @param exConsumer 异常处理
     * @param <T>        /
     * @return /
     */
    public static <T> T exToNull(ExSupplier<T> supplier, Consumer<Exception> exConsumer) {
        return exToDefault(supplier, null, exConsumer);
    }

    /**
     * 如果有异常，返回空字符串
     *
     * @param exSupplier 可能会出现异常的方法执行
     * @return /
     */
    public static String exToBlank(ExSupplier<String> exSupplier) {
        return exToDefault(exSupplier, StringConstants.EMPTY);
    }

    /**
     * 如果有异常，返回默认值
     *
     * @param exSupplier   可能会出现异常的方法执行
     * @param defaultValue 默认值
     * @param <T>          /
     * @return /
     */
    public static <T> T exToDefault(ExSupplier<T> exSupplier, T defaultValue) {
        return exToDefault(exSupplier, defaultValue, null);
    }

    /**
     * 如果有异常，执行异常处理，返回默认值
     *
     * @param exSupplier   可能会出现异常的方法执行
     * @param defaultValue 默认值
     * @param exConsumer   异常处理
     * @param <T>          /
     * @return /
     */
    public static <T> T exToDefault(ExSupplier<T> exSupplier, T defaultValue,
        Consumer<Exception> exConsumer) {
        try {
            return exSupplier.get();
        } catch (Exception e) {
            if (null != exConsumer) {
                exConsumer.accept(e);
            }
            return defaultValue;
        }
    }

    /**
     * 异常提供者
     *
     * @param <T> /
     */
    public interface ExSupplier<T> {
        /**
         * 获取返回值
         *
         * @return /
         * @throws Exception /
         */
        T get() throws Exception;
    }
}
