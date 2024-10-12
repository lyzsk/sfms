package cn.sichu.crud.core.model.query;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.sichu.core.constant.StringConstants;
import cn.sichu.core.utils.validate.ValidationUtils;
import cn.sichu.data.core.utils.SqlInjectionUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Setter;
import org.springframework.data.domain.Sort;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 排序查询条件
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Setter
@Schema(description = "排序查询条件")
public class SortQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 排序条件
     */
    @Schema(description = "排序条件", example = "createTime,desc")
    private String[] sort;

    /**
     * 解析排序条件为 Spring 分页排序实体
     *
     * @return Spring 分页排序实体
     */
    public Sort getSort() {
        if (ArrayUtil.isEmpty(sort)) {
            return Sort.unsorted();
        }
        ValidationUtils.throwIf(sort.length < 2, "排序条件非法");
        List<Sort.Order> orders = new ArrayList<>(sort.length);
        if (CharSequenceUtil.contains(sort[0], StringConstants.COMMA)) {
            // e.g "sort=createTime,desc&sort=name,asc"
            for (String s : sort) {
                List<String> sortList = CharSequenceUtil.splitTrim(s, StringConstants.COMMA);
                orders.add(this.getOrder(sortList.get(0), sortList.get(1)));
            }
        } else {
            // e.g "sort=createTime,desc"
            orders.add(this.getOrder(sort[0], sort[1]));
        }
        return Sort.by(orders);
    }

    /**
     * 获取排序条件
     *
     * @param field     字段
     * @param direction 排序方向
     * @return 排序条件
     */
    private Sort.Order getOrder(String field, String direction) {
        ValidationUtils.throwIf(SqlInjectionUtils.check(field), "排序字段包含非法字符");
        return new Sort.Order(Sort.Direction.valueOf(direction.toUpperCase()), field);
    }
}
