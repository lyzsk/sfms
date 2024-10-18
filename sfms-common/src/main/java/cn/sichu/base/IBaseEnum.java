package cn.sichu.base;

import cn.hutool.core.util.ObjectUtil;

import java.util.EnumSet;
import java.util.Objects;

/**
 * 枚举通用接口
 *
 * @author sichu huang
 * @since 2024/10/16 22:06
 */
public interface IBaseEnum<T> {
    /**
     * 根据值获取枚举
     *
     * @param value value
     * @param clazz clazz
     * @return E 枚举
     * @author sichu huang
     * @since 2024/10/16 22:06:23
     */
    static <E extends Enum<E> & IBaseEnum> E getEnumByValue(Object value, Class<E> clazz) {
        Objects.requireNonNull(value);
        EnumSet<E> allEnums = EnumSet.allOf(clazz); // 获取类型下的所有枚举
        E matchEnum =
            allEnums.stream().filter(e -> ObjectUtil.equal(e.getValue(), value)).findFirst()
                .orElse(null);
        return matchEnum;
    }

    /**
     * 根据文本标签获取值
     *
     * @param value value
     * @param clazz clazz
     * @return java.lang.String
     * @author sichu huang
     * @since 2024/10/16 22:06:48
     */
    static <E extends Enum<E> & IBaseEnum> String getLabelByValue(Object value, Class<E> clazz) {
        Objects.requireNonNull(value);
        EnumSet<E> allEnums = EnumSet.allOf(clazz); // 获取类型下的所有枚举
        E matchEnum =
            allEnums.stream().filter(e -> ObjectUtil.equal(e.getValue(), value)).findFirst()
                .orElse(null);

        String label = null;
        if (matchEnum != null) {
            label = matchEnum.getLabel();
        }
        return label;
    }

    /**
     * 根据文本标签获取值
     *
     * @param label label
     * @param clazz clazz
     * @return java.lang.Object
     * @author sichu huang
     * @since 2024/10/16 22:07:03
     */
    static <E extends Enum<E> & IBaseEnum> Object getValueByLabel(String label, Class<E> clazz) {
        Objects.requireNonNull(label);
        EnumSet<E> allEnums = EnumSet.allOf(clazz); // 获取类型下的所有枚举
        String finalLabel = label;
        E matchEnum =
            allEnums.stream().filter(e -> ObjectUtil.equal(e.getLabel(), finalLabel)).findFirst()
                .orElse(null);

        Object value = null;
        if (matchEnum != null) {
            value = matchEnum.getValue();
        }
        return value;
    }

    T getValue();

    String getLabel();
}
