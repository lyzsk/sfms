package cn.sichu.auth.model.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 令牌信息
 *
 * @author sichu huang
 * @date 2024/10/10
 **/
@Data
@Builder
@Schema(description = "令牌信息")
public class LoginResp implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 令牌
     */
    @Schema(description = "令牌",
        example = "eyJ0eXAiOiJlV1QiLCJhbGciqiJIUzI1NiJ9.eyJsb2dpblR5cGUiOiJsb29pbiIsImxvZ2luSWQiOjEsInJuU3RyIjoiSjd4SUljYnU5cmNwU09vQ3Uyc1ND1BYYTYycFRjcjAifQ.KUPOYm-2wfuLUSfEEAbpGE527fzmkAJG7sMNcQ0pUZ8")
    private String token;
}
