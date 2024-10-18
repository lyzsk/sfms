package cn.sichu.system.model.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 菜单查询对象
 *
 * @author sichu huang
 * @since 2024/10/16 22:58
 */
@Data
@Schema(description = "菜单查询对象")
public class MenuQuery {

    @Schema(description = "关键字(菜单名称)")
    private String keywords;

    @Schema(description = "状态(1->显示；0->隐藏)")
    private Integer status;
}
