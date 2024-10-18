package cn.sichu.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.format.annotation.DateTimeFormat;

import java.lang.reflect.Field;

/**
 * @author sichu huang
 * @since 2024/10/16 21:59
 */
public class DateUtils {
    /**
     * 区间日期格式化为数据库日期格式
     * <p>
     * eg：2021-01-01 → 2021-01-01 00:00:00
     *
     * @param obj                要处理的对象
     * @param startTimeFieldName 起始时间字段名
     * @param endTimeFieldName   结束时间字段名
     * @author sichu huang
     * @since 2024/10/16 22:02:12
     */
    public static void toDatabaseFormat(Object obj, String startTimeFieldName,
        String endTimeFieldName) {
        Field startTimeField = ReflectUtil.getField(obj.getClass(), startTimeFieldName);
        Field endTimeField = ReflectUtil.getField(obj.getClass(), endTimeFieldName);

        if (startTimeField != null) {
            processDateTimeField(obj, startTimeField, startTimeFieldName, "yyyy-MM-dd 00:00:00");
        }

        if (endTimeField != null) {
            processDateTimeField(obj, endTimeField, endTimeFieldName, "yyyy-MM-dd 23:59:59");
        }
    }

    /**
     * 处理日期字段
     *
     * @param obj           要处理的对象
     * @param field         字段
     * @param fieldName     字段名
     * @param targetPattern 目标数据库日期格式
     * @author sichu huang
     * @since 2024/10/16 22:01:34
     */
    private static void processDateTimeField(Object obj, Field field, String fieldName,
        String targetPattern) {
        Object fieldValue = ReflectUtil.getFieldValue(obj, fieldName);
        if (fieldValue != null) {
            // 得到原始的日期格式
            String pattern = field.isAnnotationPresent(DateTimeFormat.class) ?
                field.getAnnotation(DateTimeFormat.class).pattern() : "yyyy-MM-dd HH:mm:ss.SSS";
            // 转换为日期对象
            DateTime dateTime = DateUtil.parse(StrUtil.toString(fieldValue), pattern);
            // 转换为目标数据库日期格式
            ReflectUtil.setFieldValue(obj, fieldName, dateTime.toString(targetPattern));
        }
    }
}
