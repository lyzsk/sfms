package cn.sichu.system.enums;

import cn.sichu.base.IBaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * 通知目标类型枚举
 *
 * @author sichu huang
 * @since 2024/10/16 22:37
 */
@Getter
@Schema(enumAsRef = true)
public enum NoticeTargetTypeEnum implements IBaseEnum<Integer> {
    ALL(1, "全体"), SPECIFIED(2, "指定");

    private final Integer value;

    private final String label;

    NoticeTargetTypeEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }
}
