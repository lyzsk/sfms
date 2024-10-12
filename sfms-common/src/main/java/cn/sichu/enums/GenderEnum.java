package cn.sichu.enums;

import cn.sichu.core.enums.BaseEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 性别枚举
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Getter
@RequiredArgsConstructor
public enum GenderEnum implements BaseEnum<Integer> {

    /**
     * 未知
     **/
    UNKNOWN(0, "未知"),

    /**
     * 男
     **/
    MALE(1, "男"),

    /**
     * 女
     **/
    FEMALE(2, "女"),
    ;

    private final Integer value;
    private final String description;
}
