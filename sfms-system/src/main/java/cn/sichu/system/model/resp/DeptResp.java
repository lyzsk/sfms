package cn.sichu.system.model.resp;

import cn.sichu.crud.core.annotation.TreeField;
import cn.sichu.crud.core.model.resp.BaseDetailResp;
import cn.sichu.enums.DisEnableStatusEnum;
import cn.sichu.file.excel.converter.ExcelBaseEnumConverter;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 部门信息
 * <br/>
 * id, createUser, createUserString, createTime, disabled,
 * updateUser, updateUserString, updateTime,
 * name, parentId, status, sort, isSystem, description
 *
 * @author sichu huang
 * @date 2024/10/10
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@ExcelIgnoreUnannotated
@TreeField(value = "id", nameKey = "name")
@Schema(description = "部门信息")
public class DeptResp extends BaseDetailResp {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 名称
     **/
    @Schema(description = "名称", example = "测试部")
    @ExcelProperty(value = "名称", order = 2)
    private String name;

    /**
     * 上级部门ID
     **/
    @ExcelProperty(value = "上级部门 ID", order = 3)
    private Long parentId;

    /**
     * 状态
     **/
    @Schema(description = "上级部门 ID", example = "2")
    @ExcelProperty(value = "状态", converter = ExcelBaseEnumConverter.class, order = 5)
    private DisEnableStatusEnum status;

    /**
     * 排序
     **/
    @Schema(description = "排序", example = "3")
    @ExcelProperty(value = "排序", order = 6)
    private Integer sort;

    /**
     * 是否为系统内置数据
     **/
    @Schema(description = "是否为系统内置数据", example = "false")
    @ExcelProperty(value = "系统内置", order = 7)
    private Boolean isSystem;

    /**
     * 描述
     **/
    @Schema(description = "描述", example = "测试部描述信息")
    @ExcelProperty(value = "描述", order = 8)
    private String description;
}
