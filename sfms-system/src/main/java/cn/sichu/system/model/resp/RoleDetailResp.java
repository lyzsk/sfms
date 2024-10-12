package cn.sichu.system.model.resp;

import cn.crane4j.annotation.AssembleMethod;
import cn.crane4j.annotation.ContainerMethod;
import cn.crane4j.annotation.MappingType;
import cn.sichu.crud.core.model.resp.BaseDetailResp;
import cn.sichu.enums.DataScopeEnum;
import cn.sichu.file.excel.converter.ExcelBaseEnumConverter;
import cn.sichu.system.service.IRoleDeptService;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.List;

/**
 * 角色详情信息
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@ExcelIgnoreUnannotated
@Schema(description = "角色详情信息")
@AssembleMethod(key = "id", prop = ":deptIds", targetType = IRoleDeptService.class,
    method = @ContainerMethod(bindMethod = "listDeptIdByRoleId", type = MappingType.ORDER_OF_KEYS))
public class RoleDetailResp extends BaseDetailResp {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 名称
     **/
    @Schema(description = "名称", example = "测试人员")
    @ExcelProperty(value = "名称")
    private String name;

    /**
     * 编码
     **/
    @Schema(description = "编码", example = "test")
    @ExcelProperty(value = "编码")
    private String code;

    /**
     * 数据权限
     **/
    @Schema(description = "数据权限", example = "5")
    @ExcelProperty(value = "数据权限", converter = ExcelBaseEnumConverter.class)
    private DataScopeEnum dataScope;

    /**
     * 排序
     **/
    @Schema(description = "排序", example = "1")
    @ExcelProperty(value = "排序")
    private Integer sort;

    /**
     * 是否为系统内置数据
     **/
    @Schema(description = "是否为系统内置数据", example = "false")
    @ExcelProperty(value = "系统内置")
    private Boolean isSystem;

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

    /**
     * 描述
     **/
    @Schema(description = "描述", example = "测试人员描述信息")
    @ExcelProperty(value = "描述")
    private String description;

    /**
     * 功能权限：菜单ID列表
     **/
    @Schema(description = "功能权限：菜单 ID 列表", example = "1000,1010,1011,1012,1013,1014")
    private List<Long> menuIds;

    /**
     * 权限范围：部门ID列表
     **/
    @Schema(description = "权限范围：部门 ID 列表", example = "5")
    private List<Long> deptIds;

    @Override
    public Boolean getDisabled() {
        return this.getIsSystem();
    }
}
