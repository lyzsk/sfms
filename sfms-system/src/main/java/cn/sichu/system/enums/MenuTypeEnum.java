package cn.sichu.system.enums;

import cn.sichu.core.enums.BaseEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 菜单类型枚举
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Getter
@RequiredArgsConstructor
public enum MenuTypeEnum implements BaseEnum<Integer> {
    /**
     * 目录
     **/
    DIR(1, "目录"),

    /**
     * 菜单
     **/
    MENU(2, "菜单"),

    /**
     * 按钮
     **/
    BUTTON(3, "按钮"),
    ;

    private final Integer value;
    private final String description;
}
