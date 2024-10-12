package cn.sichu.system.model.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 仪表盘-访客地域分布信息
 *
 * @author sichu huang
 * @date 2024/10/10
 **/
@Data
@Schema(description = "仪表盘-访客地域分布信息")
public class DashboardGeoDistributionResp implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 地点列表
     **/
    @Schema(description = "地点列表", example = "[\"中国北京北京市\",\"中国广东省深圳市\"]")
    private List<String> locations;

    /**
     * 地点 IP 统计信息
     **/
    @Schema(description = "地点 IP 统计信息",
        example = "[{\"name\":\"中国北京北京市\",\"value\":1000},{\"name\":\"中国广东省深圳市\",\"value\": 500}]")
    private List<Map<String, Object>> locationIpStatistics;
}
