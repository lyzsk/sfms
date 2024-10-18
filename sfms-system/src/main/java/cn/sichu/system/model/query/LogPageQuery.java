package cn.sichu.system.model.query;

import cn.sichu.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author sichu huang
 * @since 2024/10/16 23:20
 */
@Getter
@Setter
@Schema(description = "日志分页查询对象")
public class LogPageQuery extends BasePageQuery {

    @Schema(description = "操作时间范围")
    List<String> createTime;

    @Schema(description = "关键字(日志内容/请求路径/请求方法/地区/浏览器/终端系统)")
    private String keywords;
}
