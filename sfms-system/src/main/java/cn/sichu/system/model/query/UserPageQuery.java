package cn.sichu.system.model.query;

import cn.sichu.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 用户分页查询对象
 *
 * @author sichu huang
 * @since 2024/10/16 23:00
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "用户分页查询对象")
public class UserPageQuery extends BasePageQuery {

    @Schema(description = "关键字(用户名/昵称/手机号)")
    private String keywords;

    @Schema(description = "用户状态")
    private Integer status;

    @Schema(description = "部门ID")
    private Long deptId;

    @Schema(description = "创建时间范围")
    private List<String> createTime;
}
