package cn.sichu.system.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author sichu huang
 * @since 2024/10/16 22:42
 */
@Data
@Schema(description = "文件对象")
public class FileInfo {
    @Schema(description = "文件名称")
    private String name;

    @Schema(description = "文件URL")
    private String url;
}
