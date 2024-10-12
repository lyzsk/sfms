package cn.sichu.apidoc.utils;

import cn.sichu.core.enums.BaseEnum;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 接口文档工具类
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public class DocUtils {

    private DocUtils() {
    }

    /**
     * 获取枚举值类型
     *
     * @param enumClass 枚举类型
     * @return 枚举值类型
     */
    public static String getEnumValueTypeAsString(Class<?> enumClass) {
        // 获取枚举类实现的所有接口
        Type[] interfaces = enumClass.getGenericInterfaces();
        // 定义枚举值类型的映射
        Map<Class<?>, String> typeMap =
            Map.of(Integer.class, "integer", Long.class, "long", Double.class, "number",
                String.class, "string");
        // 遍历所有接口
        for (Type type : interfaces) {
            // 检查接口是否为参数化类型并且原始类型为 BaseEnum
            if (type instanceof ParameterizedType parameterizedType
                && parameterizedType.getRawType() == BaseEnum.class) {
                Type actualType = parameterizedType.getActualTypeArguments()[0];
                // 检查实际类型参数是否为类类型，并返回对应的字符串类型
                if (actualType instanceof Class<?> actualClass) {
                    return typeMap.getOrDefault(actualClass, "string");
                }
            }
        }
        // 默认返回 "string" 类型
        return "string";
    }

    /**
     * 解析枚举值的格式
     *
     * @param enumValueType 枚举值类型
     * @return String 格式化类型
     */
    public static String resolveFormat(String enumValueType) {
        return switch (enumValueType) {
            case "integer" -> "int32";
            case "long" -> "int64";
            case "number" -> "double";
            default -> enumValueType;
        };
    }

    /**
     * 具有 RestController 注释，既检查是否继承了BaseController
     *
     * @param clazz clazz
     * @return boolean
     */
    public static boolean hasRestControllerAnnotation(Class<?> clazz) {
        // 如果注释包含 RestController 注解，则返回 true
        if (clazz.isAnnotationPresent(RestController.class)) {
            return true;
        }
        // 递归检查父类
        Class<?> superClass = clazz.getSuperclass();
        // 循环检查父类
        while (superClass != null && !superClass.equals(Object.class)) {
            // 如果父类包含 RestController 注解，则返回 true
            if (hasRestControllerAnnotation(superClass)) {
                return true;
            }
            // 递归检查接口
            superClass = superClass.getSuperclass();
        }
        return false;
    }

    /**
     * 获取枚举描述 Map
     *
     * @param enumClass 枚举类型
     * @return 枚举描述 Map
     */
    public static Map<Object, String> getDescMap(Class<?> enumClass) {
        BaseEnum[] enums = (BaseEnum[])enumClass.getEnumConstants();
        return Arrays.stream(enums).collect(
            Collectors.toMap(BaseEnum::getValue, BaseEnum::getDescription, (a, b) -> a,
                LinkedHashMap::new));
    }
}