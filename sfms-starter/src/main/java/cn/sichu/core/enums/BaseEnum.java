package cn.sichu.core.enums;

import java.io.Serializable;

/**
 * 枚举接口
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public interface BaseEnum<T extends Serializable> {

    /**
     * 枚举值
     *
     * @return 枚举值
     */
    T getValue();

    /**
     * 枚举描述
     *
     * @return 枚举描述
     */
    String getDescription();

    /**
     * 颜色
     *
     * @return 颜色
     */
    default String getColor() {
        return null;
    }
}
