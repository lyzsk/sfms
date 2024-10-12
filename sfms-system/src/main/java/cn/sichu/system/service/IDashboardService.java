package cn.sichu.system.service;

import cn.sichu.system.model.resp.*;

import java.util.List;

/**
 * 仪表盘业务接口
 *
 * @author sichu huang
 * @date 2024/10/10
 **/
public interface IDashboardService {

    /**
     * 查询总计信息
     *
     * @return cn.sichu.system.model.resp.DashboardTotalResp 总计信息
     * @author sichu huang
     * @date 2024/10/12
     **/
    DashboardTotalResp getTotal();

    /**
     * 查询访问趋势信息
     *
     * @param days 日期数
     * @return java.util.List<cn.sichu.system.model.resp.DashboardAccessTrendResp> 访问趋势信息
     * @author sichu huang
     * @date 2024/10/12
     **/
    List<DashboardAccessTrendResp> listAccessTrend(Integer days);

    /**
     * 查询热门模块列表
     *
     * @return java.util.List<cn.sichu.system.model.resp.DashboardPopularModuleResp> 热门模块列表
     * @author sichu huang
     * @date 2024/10/12
     **/
    List<DashboardPopularModuleResp> listPopularModule();

    /**
     * 查询访客地域分布信息
     *
     * @return cn.sichu.system.model.resp.DashboardGeoDistributionResp 访客地域分布信息
     * @author sichu huang
     * @date 2024/10/12
     **/
    DashboardGeoDistributionResp getGeoDistribution();

    /**
     * 查询公告列表
     *
     * @return java.util.List<cn.sichu.system.model.resp.DashboardNoticeResp> 公告列表
     * @author sichu huang
     * @date 2024/10/12
     **/
    List<DashboardNoticeResp> listNotice();
}
