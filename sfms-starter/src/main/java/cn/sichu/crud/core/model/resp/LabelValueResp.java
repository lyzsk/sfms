package cn.sichu.crud.core.model.resp;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 键值对信息
 *
 * @param <T>
 * @author sichu huang
 * @date 2024/10/11
 **/
@Setter
@Getter
@Schema(description = "键值对信息")
public class LabelValueResp<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 标签
     */
    @Schema(description = "标签", example = "男")
    private String label;

    /**
     * 值
     */
    @Schema(description = "值", example = "1")
    private T value;

    /**
     * 是否禁用
     */
    @Schema(description = "是否禁用", example = "false")
    private Boolean disabled;

    /**
     * 扩展
     */
    @Schema(description = "扩展")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object extend;

    public LabelValueResp() {
    }

    public LabelValueResp(String label, T value) {
        this.label = label;
        this.value = value;
    }

    public LabelValueResp(String label, T value, Object extend) {
        this.label = label;
        this.value = value;
        this.extend = extend;
    }

    public LabelValueResp(String label, T value, Boolean disabled) {
        this.label = label;
        this.value = value;
        this.disabled = disabled;
    }

}
