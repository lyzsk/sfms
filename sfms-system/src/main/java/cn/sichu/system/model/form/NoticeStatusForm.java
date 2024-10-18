package cn.sichu.system.model.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author sichu huang
 * @since 2024/10/16 22:53
 */
@Getter
@Setter
@Schema(description = "用户公告状态表单对象")
public class NoticeStatusForm implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

}
