package cn.sichu.core.utils;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.TypeUtil;

import java.lang.reflect.Type;

/**
 * 类工具类
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public class ClassUtils {

    private ClassUtils() {
    }

    /**
     * 获得给定类的所有泛型参数
     *
     * @param clazz 被检查的类，必须是已经确定泛型类型的类
     * @return {@link Class}[]
     */
    public static Class<?>[] getTypeArguments(Class<?> clazz) {
        final Type[] typeArguments = TypeUtil.getTypeArguments(clazz);
        if (ArrayUtil.isEmpty(typeArguments)) {
            return new Class[0];
        }
        final Class<?>[] classes = new Class<?>[typeArguments.length];
        for (int i = 0; i < typeArguments.length; i++) {
            classes[i] = TypeUtil.getClass(typeArguments[i]);
        }
        return classes;
    }
}
