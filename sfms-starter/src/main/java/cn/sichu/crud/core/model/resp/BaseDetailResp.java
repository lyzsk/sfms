package cn.sichu.crud.core.model.resp;

import cn.crane4j.annotation.Assemble;
import cn.crane4j.annotation.Mapping;
import cn.crane4j.annotation.condition.ConditionOnPropertyNotNull;
import cn.sichu.crud.core.constant.ContainerPool;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.util.Date;

/**
 * 详情响应基类
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Setter
@Getter
public class BaseDetailResp extends BaseResp {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 修改人
     */
    @JsonIgnore
    @ConditionOnPropertyNotNull
    @Assemble(container = ContainerPool.USER_NICKNAME, props = @Mapping(ref = "updateUserString"))
    private Long updateUser;

    /**
     * 修改人
     */
    @Schema(description = "修改人", example = "李四")
    @ExcelProperty(value = "修改人", order = Integer.MAX_VALUE - 2)
    private String updateUserString;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间", example = "2023-08-08 08:08:08.000", type = "string")
    @ExcelProperty(value = "修改时间", order = Integer.MAX_VALUE - 1)
    private Date updateTime;

}
