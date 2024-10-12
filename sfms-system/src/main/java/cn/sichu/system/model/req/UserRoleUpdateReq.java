package cn.sichu.system.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 用户角色修改信息
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Data
@Schema(description = "用户角色修改信息")
public class UserRoleUpdateReq implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色ID 列表
     **/
    @Schema(description = "所属角色", example = "1,2")
    @NotEmpty(message = "所属角色不能为空")
    private List<Long> roleIds;
}
