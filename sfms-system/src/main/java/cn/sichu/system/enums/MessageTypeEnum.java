package cn.sichu.system.enums;

import cn.sichu.constant.UiConstants;
import cn.sichu.core.enums.BaseEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 消息类型枚举
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Getter
@RequiredArgsConstructor
public enum MessageTypeEnum implements BaseEnum<Integer> {

    /**
     * 安全消息
     **/
    SECURITY(1, "安全消息", UiConstants.COLOR_PRIMARY),
    ;

    private final Integer value;
    private final String description;
    private final String color;
}
