package cn.sichu.enums;

import lombok.Getter;

/**
 * 环境枚举
 *
 * @author sichu huang
 * @since 2024/10/16 22:20
 */
@Getter
public enum EnvEnum {
    DEV("dev", "开发环境"), PROD("prod", "生产环境");

    private final String value;

    private final String label;

    EnvEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }
}
