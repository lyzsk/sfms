package cn.sichu.system.model.query;

import cn.sichu.data.core.annotation.Query;
import cn.sichu.data.core.enums.QueryType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 公告查询条件
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Data
@Schema(description = "公告查询条件")
public class NoticeQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 标题
     **/
    @Schema(description = "标题", example = "这是公告标题")
    @Query(type = QueryType.LIKE)
    private String title;

    /**
     * 类型
     **/
    @Schema(description = "类型", example = "1")
    private String type;
}