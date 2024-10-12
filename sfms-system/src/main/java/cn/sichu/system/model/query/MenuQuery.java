package cn.sichu.system.model.query;

import cn.sichu.data.core.annotation.Query;
import cn.sichu.data.core.enums.QueryType;
import cn.sichu.enums.DisEnableStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 菜单查询条件
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Data
@NoArgsConstructor
@Schema(description = "菜单查询条件")
public class MenuQuery implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 标题
     **/
    @Schema(description = "标题", example = "用户管理")
    @Query(type = QueryType.LIKE)
    private String title;

    /**
     * 状态
     **/
    @Schema(description = "状态", example = "1")
    private DisEnableStatusEnum status;

    public MenuQuery(DisEnableStatusEnum status) {
        this.status = status;
    }
}
