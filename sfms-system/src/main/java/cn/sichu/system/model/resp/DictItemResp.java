package cn.sichu.system.model.resp;

import cn.sichu.crud.core.model.resp.BaseDetailResp;
import cn.sichu.enums.DisEnableStatusEnum;
import cn.sichu.file.excel.converter.ExcelBaseEnumConverter;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 字典项信息
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "字典项信息")
public class DictItemResp extends BaseDetailResp {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 标签
     **/
    @Schema(description = "标签", example = "通知")
    private String label;

    /**
     * 值
     **/
    @Schema(description = "值", example = "1")
    private String value;

    /**
     * 标签颜色
     **/
    @Schema(description = "标签颜色", example = "blue")
    private String color;

    /**
     * 状态
     **/
    @Schema(description = "状态", example = "1")
    @ExcelProperty(value = "状态", converter = ExcelBaseEnumConverter.class)
    private DisEnableStatusEnum status;

    /**
     * 排序
     **/
    @Schema(description = "排序", example = "1")
    private Integer sort;

    /**
     * 描述
     **/
    @Schema(description = "描述", example = "通知描述信息")
    private String description;

    /**
     * 字典 ID
     **/
    @Schema(description = "字典 ID", example = "1")
    private Long dictId;
}
