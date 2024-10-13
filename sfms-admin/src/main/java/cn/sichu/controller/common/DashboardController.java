package cn.sichu.controller.common;

import cn.sichu.constant.CacheConstants;
import cn.sichu.core.utils.validate.ValidationUtils;
import cn.sichu.log.annotation.Log;
import cn.sichu.system.model.resp.*;
import cn.sichu.system.service.IDashboardService;
import com.alicp.jetcache.anno.CachePenetrationProtect;
import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 仪表盘 API
 *
 * @author sichu huang
 * @date 2024/10/13
 **/
@Tag(name = "仪表盘 API")
@Log(ignore = true)
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class DashboardController {

    private final IDashboardService dashboardService;

    @Operation(summary = "查询总计信息", description = "查询总计信息")
    @GetMapping("/total")
    public DashboardTotalResp getTotal() {
        return dashboardService.getTotal();
    }

    @Operation(summary = "查询访问趋势信息", description = "查询访问趋势信息")
    @Parameter(name = "days", description = "日期数", example = "30", in = ParameterIn.PATH)
    @GetMapping("/access/trend/{days}")
    @CachePenetrationProtect
    @CacheRefresh(refresh = 7200)
    @Cached(key = "#days", name = CacheConstants.DASHBOARD_KEY_PREFIX, cacheType = CacheType.BOTH,
        syncLocal = true)
    public List<DashboardAccessTrendResp> listAccessTrend(@PathVariable Integer days) {
        ValidationUtils.throwIf(7 != days && 30 != days, "仅支持查询近 7/30 天访问趋势信息");
        return dashboardService.listAccessTrend(days);
    }

    @Operation(summary = "查询热门模块列表", description = "查询热门模块列表")
    @GetMapping("/popular/module")
    public List<DashboardPopularModuleResp> listPopularModule() {
        return dashboardService.listPopularModule();
    }

    @Operation(summary = "查询访客地域分布信息", description = "查询访客地域分布信息")
    @GetMapping("/geo/distribution")
    public DashboardGeoDistributionResp getGeoDistribution() {
        return dashboardService.getGeoDistribution();
    }

    @Operation(summary = "查询公告列表", description = "查询公告列表")
    @GetMapping("/notice")
    public List<DashboardNoticeResp> listNotice() {
        return dashboardService.listNotice();
    }
}
