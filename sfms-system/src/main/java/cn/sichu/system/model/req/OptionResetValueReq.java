package cn.sichu.system.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 参数重置信息
 *
 * @author sichu huang
 * @date 2024/10/10
 **/
@Data
@Schema(description = "参数重置信息")
public class OptionResetValueReq implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 键列表
     **/
    @Schema(description = "键列表", example = "SITE_TITLE,SITE_COPYRIGHT")
    private List<String> code;
    
    /**
     * 类别
     **/
    @Schema(description = "类别", example = "SITE")
    private String category;
}