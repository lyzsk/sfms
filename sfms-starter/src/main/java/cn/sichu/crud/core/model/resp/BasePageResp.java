package cn.sichu.crud.core.model.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 分页信息
 *
 * @param <T> 列表数据类型
 * @author sichu huang
 * @date 2024/10/11
 **/
@Setter
@Getter
@Schema(description = "分页信息")
public class BasePageResp<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 列表数据
     */
    @Schema(description = "列表数据")
    private List<T> list;

    /**
     * 总记录数
     */
    @Schema(description = "总记录数", example = "10")
    private long total;

    public BasePageResp() {
    }

    public BasePageResp(final List<T> list, final long total) {
        this.list = list;
        this.total = total;
    }

}
