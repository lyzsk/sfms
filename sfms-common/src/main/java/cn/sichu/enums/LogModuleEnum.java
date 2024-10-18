package cn.sichu.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * 日志模块枚举
 *
 * @author sichu huang
 * @since 2024/10/16 22:21
 */
@Schema(enumAsRef = true)
@Getter
public enum LogModuleEnum {
    LOGIN("登录"), USER("用户"), DEPT("部门"), ROLE("角色"), MENU("菜单"), DICT("字典"), OTHER(
        "其他");

    @JsonValue
    private final String moduleName;

    LogModuleEnum(String moduleName) {
        this.moduleName = moduleName;
    }
}
