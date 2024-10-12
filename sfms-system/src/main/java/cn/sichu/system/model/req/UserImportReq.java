package cn.sichu.system.model.req;

import cn.sichu.crud.core.model.req.BaseReq;
import cn.sichu.enums.DisEnableStatusEnum;
import cn.sichu.system.enums.ImportPolicyEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 用户导入参数
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "用户导入参数")
public class UserImportReq extends BaseReq {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 导入会话KEY
     **/
    @Schema(description = "导入会话KEY", example = "1b9d6bcd-bbfd-4b2d-9b5d-ab8dfbbd4bed")
    @NotBlank(message = "导入已过期，请重新上传")
    private String importKey;

    /**
     * 用户重复策略
     **/
    @Schema(description = "重复用户策略", example = "1")
    @NotNull(message = "重复用户策略不能为空")
    private ImportPolicyEnum duplicateUser;

    /**
     * 重复邮箱策略
     **/
    @Schema(description = "重复邮箱策略", example = "1")
    @NotNull(message = "重复邮箱策略不能为空")
    private ImportPolicyEnum duplicateEmail;

    /**
     * 重复手机策略
     **/
    @Schema(description = "重复手机策略", example = "1")
    @NotNull(message = "重复手机策略不能为空")
    private ImportPolicyEnum duplicatePhone;

    /**
     * 默认状态
     **/
    @Schema(description = "默认状态", example = "1")
    private DisEnableStatusEnum defaultStatus;
}
