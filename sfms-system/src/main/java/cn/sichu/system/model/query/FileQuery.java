package cn.sichu.system.model.query;

import cn.sichu.data.core.annotation.Query;
import cn.sichu.data.core.enums.QueryType;
import cn.sichu.system.enums.FileTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 文件查询条件
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Data
@Schema(description = "文件查询条件")
public class FileQuery implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 名称
     **/
    @Schema(description = "名称", example = "图片")
    @Query(type = QueryType.LIKE)
    private String name;
    /**
     * 类型
     **/
    @Schema(description = "类型", example = "2")
    private FileTypeEnum type;
}
