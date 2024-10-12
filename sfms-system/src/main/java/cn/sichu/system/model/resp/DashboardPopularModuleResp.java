package cn.sichu.system.model.resp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 仪表盘-热门模块信息
 *
 * @author sichu huang
 * @date 2024/10/10
 **/
@Data
@Schema(description = "仪表盘-热门模块信息")
public class DashboardPopularModuleResp implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 模块
     **/
    @Schema(description = "模块", example = "角色管理")
    private String module;

    /**
     * 浏览量（PV）
     **/
    @Schema(description = "浏览量（PV）", example = "1234")
    private Long pvCount;

    /**
     * 较昨日新增 PV（百分比）
     **/
    @Schema(description = "较昨日新增（百分比）", example = "23.4")
    private BigDecimal newPvFromYesterday;

    /**
     * 今日浏览量（PV）
     **/
    @JsonIgnore
    private Long todayPvCount;

    /**
     * 昨日浏览量（PV）
     **/
    @JsonIgnore
    private Long yesterdayPvCount;
}
