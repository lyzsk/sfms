package cn.sichu.system.enums;

import cn.sichu.core.enums.BaseEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 存储类型枚举
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Getter
@RequiredArgsConstructor
public enum StorageTypeEnum implements BaseEnum<Integer> {
    /**
     * 兼容S3协议存储
     **/
    S3(1, "兼容S3协议存储"),
    
    /**
     * 本地存储
     **/
    LOCAL(2, "本地存储"),
    ;

    private final Integer value;
    private final String description;
}
