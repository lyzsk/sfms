package cn.sichu.enums;

import cn.sichu.constant.UiConstants;
import cn.sichu.core.enums.BaseEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 成功/失败状态枚举
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Getter
@RequiredArgsConstructor
public enum SuccessFailureStatusEnum implements BaseEnum<Integer> {
    /**
     * 失败
     **/
    FAILURE(0, "失败", UiConstants.COLOR_ERROR),
    /**
     * 成功
     **/
    SUCCESS(1, "成功", UiConstants.COLOR_SUCCESS),
    ;

    private final Integer value;
    private final String description;
    private final String color;
}
