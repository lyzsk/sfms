package cn.sichu.system.model.resp;

import cn.sichu.system.enums.FileTypeEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 文件资源统计信息
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "文件资源统计信息")
public class FileStatisticsResp implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 文件类型
     **/
    @Schema(description = "类型", example = "2")
    private FileTypeEnum type;

    /**
     * 大小（字节）
     **/
    @Schema(description = "大小（字节）", example = "4096")
    private Long size;

    /**
     * 数量
     **/
    @Schema(description = "数量", example = "1000")
    private Long number;

    /**
     * 分类数据
     **/
    @Schema(description = "分类数据")
    private List<FileStatisticsResp> data;
}
