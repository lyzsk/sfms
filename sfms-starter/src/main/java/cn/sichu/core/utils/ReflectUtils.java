package cn.sichu.core.utils;

import cn.hutool.core.util.ReflectUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 反射工具类
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public class ReflectUtils {

    private ReflectUtils() {
    }

    /**
     * 获得一个类中所有非静态字段名列表，包括其父类中的字段<br>
     * 如果子类与父类中存在同名字段，则这两个字段同时存在，子类字段在前，父类字段在后。
     *
     * @param beanClass 类
     * @return 非静态字段名列表
     * @throws SecurityException 安全检查异常
     */
    public static List<String> getNonStaticFieldsName(Class<?> beanClass) throws SecurityException {
        List<Field> nonStaticFields = getNonStaticFields(beanClass);
        return nonStaticFields.stream().map(Field::getName).collect(Collectors.toList());
    }

    /**
     * 获得一个类中所有非静态字段列表，包括其父类中的字段<br>
     * 如果子类与父类中存在同名字段，则这两个字段同时存在，子类字段在前，父类字段在后。
     *
     * @param beanClass 类
     * @return 非静态字段列表
     * @throws SecurityException 安全检查异常
     */
    public static List<Field> getNonStaticFields(Class<?> beanClass) throws SecurityException {
        Field[] fields = ReflectUtil.getFields(beanClass);
        return Arrays.stream(fields).filter(f -> !Modifier.isStatic(f.getModifiers()))
            .collect(Collectors.toList());
    }
}
