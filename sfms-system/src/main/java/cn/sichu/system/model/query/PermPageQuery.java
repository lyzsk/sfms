package cn.sichu.system.model.query;

import cn.sichu.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 权限分页查询对象
 *
 * @author sichu huang
 * @since 2024/10/16 23:00
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema
public class PermPageQuery extends BasePageQuery {

    @Schema(description = "权限名称")
    private String name;

    @Schema(description = "菜单ID")
    private Long menuId;
}
