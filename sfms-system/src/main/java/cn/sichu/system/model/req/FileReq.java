package cn.sichu.system.model.req;

import cn.sichu.crud.core.model.req.BaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;

/**
 * 修改文件信息
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "修改文件信息")
public class FileReq extends BaseReq {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 名称
     **/
    @Schema(description = "名称", example = "test123")
    @NotBlank(message = "文件名称不能为空")
    @Length(max = 255, message = "文件名称长度不能超过 {max} 个字符")
    private String name;
}
