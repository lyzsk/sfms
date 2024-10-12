package cn.sichu.system.model.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 头像信息
 *
 * @author sichu huang
 * @date 2024/10/10
 **/
@Data
@Builder
@Schema(description = "头像信息")
public class AvatarResp implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 头像地址
     **/
    @Schema(description = "头像地址",
        example = "https://himg.bdimg.com/sys/portrait/item/public.1.81ac9a9e.rf1ix17UfughLQjNo7XQ_w.jpg")
    private String avatar;
}
