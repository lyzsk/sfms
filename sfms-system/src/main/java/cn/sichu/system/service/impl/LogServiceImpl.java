package cn.sichu.system.service.impl;

import cn.sichu.system.mapper.LogMapper;
import cn.sichu.system.model.bo.VisitCount;
import cn.sichu.system.model.entity.Log;
import cn.sichu.system.model.query.LogPageQuery;
import cn.sichu.system.model.vo.LogPageVO;
import cn.sichu.system.model.vo.VisitStatsVO;
import cn.sichu.system.model.vo.VisitTrendVO;
import cn.sichu.system.service.LogService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author sichu huang
 * @since 2024/10/17 15:49
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {

    @Override
    public Page<LogPageVO> getLogPage(LogPageQuery queryParams) {
        System.err.println(queryParams);
        return this.baseMapper.getLogPage(
            new Page<>(queryParams.getPageNum(), queryParams.getPageSize()), queryParams);
    }

    @Override
    public VisitTrendVO getVisitTrend(LocalDate startDate, LocalDate endDate) {
        VisitTrendVO visitTrend = new VisitTrendVO();
        List<String> dates = new ArrayList<>();

        // 获取日期范围内的日期
        while (!startDate.isAfter(endDate)) {
            dates.add(startDate.toString());
            startDate = startDate.plusDays(1);
        }
        visitTrend.setDates(dates);

        // 获取访问量和访问 IP 数的统计数据
        List<VisitCount> pvCounts = this.baseMapper.getPvCounts(dates.get(0) + " 00:00:00",
            dates.get(dates.size() - 1) + " 23:59:59");
        List<VisitCount> ipCounts = this.baseMapper.getIpCounts(dates.get(0) + " 00:00:00",
            dates.get(dates.size() - 1) + " 23:59:59");

        // 将统计数据转换为 Map
        Map<String, Integer> pvMap =
            pvCounts.stream().collect(Collectors.toMap(VisitCount::getDate, VisitCount::getCount));
        Map<String, Integer> ipMap =
            ipCounts.stream().collect(Collectors.toMap(VisitCount::getDate, VisitCount::getCount));

        // 匹配日期和访问量/访问 IP 数
        List<Integer> pvList = new ArrayList<>();
        List<Integer> ipList = new ArrayList<>();

        for (String date : dates) {
            pvList.add(pvMap.getOrDefault(date, 0));
            ipList.add(ipMap.getOrDefault(date, 0));
        }

        visitTrend.setPvList(pvList);
        visitTrend.setIpList(ipList);

        return visitTrend;
    }

    @Override
    public List<VisitStatsVO> getVisitStats() {
        List<VisitStatsVO> list = new ArrayList<>();
        // 访问量
        VisitStatsVO pvStats = this.baseMapper.getPvStats();
        pvStats.setTitle("浏览量");
        pvStats.setType("pv");
        pvStats.setGranularityLabel("日");
        list.add(pvStats);
        // 访客数
        VisitStatsVO uvStats = new VisitStatsVO();
        uvStats.setTitle("访客数");
        uvStats.setType("uv");
        uvStats.setTodayCount(100);
        uvStats.setTotalCount(2000);
        uvStats.setGrowthRate(BigDecimal.ZERO);
        uvStats.setGranularityLabel("日");
        list.add(uvStats);
        // IP数
        VisitStatsVO ipStats = this.baseMapper.getIpStats();
        ipStats.setTitle("IP数");
        ipStats.setType("ip");
        ipStats.setGranularityLabel("日");
        list.add(ipStats);
        return list;
    }
}
