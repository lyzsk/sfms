package cn.sichu.system.model.req;

import cn.sichu.constant.RegexConstants;
import cn.sichu.crud.core.model.req.BaseReq;
import cn.sichu.enums.DataScopeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sichu huang
 * @date 2024/10/11
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "创建或修改角色信息")
public class RoleReq extends BaseReq {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 名称
     **/
    @Schema(description = "名称", example = "测试人员")
    @NotBlank(message = "名称不能为空")
    @Pattern(regexp = RegexConstants.GENERAL_NAME,
        message = "名称长度为 2-30 个字符，支持中文、字母、数字、下划线，短横线")
    private String name;

    /**
     * 编码
     **/
    @Schema(description = "编码", example = "test")
    @NotBlank(message = "编码不能为空")
    @Pattern(regexp = RegexConstants.GENERAL_CODE,
        message = "编码长度为 2-30 个字符，支持大小写字母、数字、下划线，以字母开头")
    private String code;

    /**
     * 排序
     **/
    @Schema(description = "排序", example = "1")
    @Min(value = 1, message = "排序最小值为 {value}")
    private Integer sort;

    /**
     * 描述
     **/
    @Schema(description = "描述", example = "测试人员描述信息")
    @Length(max = 200, message = "描述长度不能超过 {max} 个字符")
    private String description;

    /**
     * 功能权限：菜单ID列表
     **/
    @Schema(description = "功能权限：菜单 ID 列表", example = "1000,1010,1011,1012,1013,1014")
    private List<Long> menuIds = new ArrayList<>();

    /**
     * 数据权限
     **/
    @Schema(description = "数据权限", example = "5")
    private DataScopeEnum dataScope;

    /**
     * 权限范围：部门ID列表
     **/
    @Schema(description = "权限范围：部门 ID 列表", example = "5")
    private List<Long> deptIds = new ArrayList<>();

    /**
     * 菜单选择是否父子节点关联
     **/
    @Schema(description = "菜单选择是否父子节点关联", example = "false")
    private Boolean menuCheckStrictly;

    /**
     * 部门选择是否父子节点关联
     **/
    @Schema(description = "部门选择是否父子节点关联", example = "false")
    private Boolean deptCheckStrictly;
}
