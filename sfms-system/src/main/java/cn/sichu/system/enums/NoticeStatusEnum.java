package cn.sichu.system.enums;

import cn.sichu.constant.UiConstants;
import cn.sichu.core.enums.BaseEnum;
import cn.sichu.core.utils.DateUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.text.ParseException;
import java.util.Date;

/**
 * 公告状态枚举
 *
 * @author Charles7c
 * @since 2023/8/20 10:55
 */
@Getter
@RequiredArgsConstructor
public enum NoticeStatusEnum implements BaseEnum<Integer> {

    /**
     * 待发布
     **/
    PENDING_RELEASE(1, "待发布", UiConstants.COLOR_PRIMARY),

    /**
     * 已发布
     **/
    PUBLISHED(2, "已发布", UiConstants.COLOR_SUCCESS),

    /**
     * 已过期
     **/
    EXPIRED(3, "已过期", UiConstants.COLOR_ERROR),
    ;

    private final Integer value;
    private final String description;
    private final String color;

    /**
     * 获取公告状态
     *
     * @param effectiveTime 生效时间
     * @param terminateTime 终止时间
     * @return cn.sichu.system.enums.NoticeStatusEnum 公告状态
     * @author sichu huang
     * @date 2024/10/11
     **/
    public static NoticeStatusEnum getStatus(Date effectiveTime, Date terminateTime)
        throws ParseException {
        Date now = DateUtils.parseMillisecond(new Date());
        if (effectiveTime != null && effectiveTime.after(now)) {
            return PENDING_RELEASE;
        }
        if (terminateTime != null && terminateTime.before(now)) {
            return EXPIRED;
        }
        return PUBLISHED;
    }
}
