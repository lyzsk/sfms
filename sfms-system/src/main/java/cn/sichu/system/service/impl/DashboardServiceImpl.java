package cn.sichu.system.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.NumberUtil;
import cn.sichu.system.model.resp.*;
import cn.sichu.system.service.IDashboardService;
import cn.sichu.system.service.ILogService;
import cn.sichu.system.service.INoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 仪表盘业务实现
 *
 * @author sichu huang
 * @date 2024/10/10
 **/
@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements IDashboardService {

    private final ILogService logService;
    private final INoticeService noticeService;

    @Override
    public DashboardTotalResp getTotal() {
        DashboardTotalResp totalResp = logService.getDashboardTotal();
        Long todayPvCount = totalResp.getTodayPvCount();
        Long yesterdayPvCount = totalResp.getYesterdayPvCount();
        BigDecimal newPvCountFromYesterday = NumberUtil.sub(todayPvCount, yesterdayPvCount);
        BigDecimal newPvFromYesterday = (0 == yesterdayPvCount) ? BigDecimal.valueOf(100) :
            NumberUtil.round(
                NumberUtil.mul(NumberUtil.div(newPvCountFromYesterday, yesterdayPvCount), 100), 1);
        totalResp.setNewPvFromYesterday(newPvFromYesterday);
        return totalResp;
    }

    @Override
    public List<DashboardAccessTrendResp> listAccessTrend(Integer days) {
        return logService.listDashboardAccessTrend(days);
    }

    @Override
    public List<DashboardPopularModuleResp> listPopularModule() {
        List<DashboardPopularModuleResp> popularModuleList =
            logService.listDashboardPopularModule();
        for (DashboardPopularModuleResp popularModule : popularModuleList) {
            Long todayPvCount = popularModule.getTodayPvCount();
            Long yesterdayPvCount = popularModule.getYesterdayPvCount();
            BigDecimal newPvCountFromYesterday = NumberUtil.sub(todayPvCount, yesterdayPvCount);
            BigDecimal newPvFromYesterday = (0 == yesterdayPvCount) ? BigDecimal.valueOf(100) :
                NumberUtil.round(
                    NumberUtil.mul(NumberUtil.div(newPvCountFromYesterday, yesterdayPvCount), 100),
                    1);
            popularModule.setNewPvFromYesterday(newPvFromYesterday);
        }
        return popularModuleList;
    }

    @Override
    public DashboardGeoDistributionResp getGeoDistribution() {
        List<Map<String, Object>> locationIpStatistics = logService.listDashboardGeoDistribution();
        DashboardGeoDistributionResp geoDistribution = new DashboardGeoDistributionResp();
        geoDistribution.setLocationIpStatistics(locationIpStatistics);
        geoDistribution.setLocations(
            locationIpStatistics.stream().map(m -> Convert.toStr(m.get("name"))).toList());
        return geoDistribution;
    }

    @Override
    public List<DashboardNoticeResp> listNotice() {
        return noticeService.listDashboard();
    }
}
