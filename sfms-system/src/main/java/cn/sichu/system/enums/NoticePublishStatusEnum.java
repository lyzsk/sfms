package cn.sichu.system.enums;

import cn.sichu.base.IBaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * 通告发布状态枚举
 *
 * @author sichu huang
 * @since 2024/10/16 22:37
 */
@Getter
@Schema(enumAsRef = true)
public enum NoticePublishStatusEnum implements IBaseEnum<Integer> {
    UNPUBLISHED(0, "未发布"), PUBLISHED(1, "已发布"), REVOKED(-1, "已撤回");

    private final Integer value;

    private final String label;

    NoticePublishStatusEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }
}
