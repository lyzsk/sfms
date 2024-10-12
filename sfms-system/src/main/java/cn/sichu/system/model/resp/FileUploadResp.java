package cn.sichu.system.model.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 文件上传响应信息
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Data
@Builder
@Schema(description = "文件上传响应信息")
public class FileUploadResp implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 文件 URL
     **/
    @Schema(description = "文件 URL",
        example = "http://localhost:8000/file/65e87dc3fb377a6fb58bdece.jpg")
    private String url;
}
