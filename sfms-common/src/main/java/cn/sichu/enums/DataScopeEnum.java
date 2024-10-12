package cn.sichu.enums;

import cn.sichu.core.enums.BaseEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 数据权限枚举
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Getter
@RequiredArgsConstructor
public enum DataScopeEnum implements BaseEnum<Integer> {

    /**
     * 全部数据权限
     **/
    ALL(1, "全部数据权限"),

    /**
     * 本部门及以下数据权限
     **/
    DEPT_AND_CHILD(2, "本部门及以下数据权限"),

    /**
     * 本部门数据权限
     **/
    DEPT(3, "本部门数据权限"),

    /**
     * 仅本人数据权限
     **/
    SELF(4, "仅本人数据权限"),

    /**
     * 自定义数据权限
     **/
    CUSTOM(5, "自定义数据权限"),
    ;

    private final Integer value;
    private final String description;
}
