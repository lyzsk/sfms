package cn.sichu.crud.core.model.query;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springdoc.core.annotations.ParameterObject;

import java.io.Serial;

/**
 * 分页查询条件
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Setter
@Getter
@ParameterObject
@Schema(description = "分页查询条件")
public class PageQuery extends SortQuery {

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 默认页码：1
     */
    private static final int DEFAULT_PAGE = 1;
    /**
     * 默认每页条数：10
     */
    private static final int DEFAULT_SIZE = 10;

    /**
     * 页码
     */
    @Schema(description = "页码", example = "1")
    @Min(value = 1, message = "页码最小值为 {value}")
    private Integer page = DEFAULT_PAGE;

    /**
     * 每页条数
     */
    @Schema(description = "每页条数", example = "10")
    @Range(min = 1, max = 1000, message = "每页条数（取值范围 {min}-{max}）")
    private Integer size = DEFAULT_SIZE;

}
