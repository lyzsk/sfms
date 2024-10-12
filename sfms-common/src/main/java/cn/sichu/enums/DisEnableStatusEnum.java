package cn.sichu.enums;

import cn.sichu.constant.UiConstants;
import cn.sichu.core.enums.BaseEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 启用/禁用状态枚举
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Getter
@RequiredArgsConstructor
public enum DisEnableStatusEnum implements BaseEnum<Integer> {
    /**
     * 禁用
     **/
    DISABLE(0, "禁用", UiConstants.COLOR_ERROR),
    /**
     * 启用
     **/
    ENABLE(1, "启用", UiConstants.COLOR_SUCCESS),
    ;

    private final Integer value;
    private final String description;
    private final String color;
}
