package cn.sichu.crud.core.model.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * ID 响应信息
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Setter
@Getter
public class BaseIdResp<T extends Serializable> implements Serializable {

    /**
     * ID
     */
    @Schema(description = "ID", example = "1")
    private T id;

    BaseIdResp(final T id) {
        this.id = id;
    }

    public static <T extends Serializable> BaseIdRespBuilder<T> builder() {
        return new BaseIdRespBuilder();
    }

    public static class BaseIdRespBuilder<T extends Serializable> {
        private T id;

        BaseIdRespBuilder() {
        }

        public BaseIdRespBuilder<T> id(final T id) {
            this.id = id;
            return this;
        }

        public BaseIdResp<T> build() {
            return new BaseIdResp(this.id);
        }
    }
}
