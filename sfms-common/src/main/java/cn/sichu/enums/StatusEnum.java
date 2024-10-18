package cn.sichu.enums;

import cn.sichu.base.IBaseEnum;
import lombok.Getter;

/**
 * 状态枚举
 *
 * @author sichu huang
 * @since 2024/10/16 22:21
 */
@Getter
public enum StatusEnum implements IBaseEnum<Integer> {
    ENABLE(1, "启用"), DISABLE(0, "禁用");

    private final Integer value;

    private final String label;

    StatusEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }
}
