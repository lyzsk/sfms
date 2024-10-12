package cn.sichu.system.enums;

import cn.sichu.core.enums.BaseEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 操作状态枚举
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Getter
@RequiredArgsConstructor
public enum LogStatusEnum implements BaseEnum<Integer> {

    /**
     * 成功
     **/
    SUCCESS(1, "成功"),

    /**
     * 失败
     **/
    FAILURE(2, "失败"),
    ;

    private final Integer value;
    private final String description;
}
