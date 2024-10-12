package cn.sichu.system.service;

import cn.sichu.crud.core.model.query.PageQuery;
import cn.sichu.crud.core.model.query.SortQuery;
import cn.sichu.crud.mp.model.resp.PageResp;
import cn.sichu.system.model.query.LogQuery;
import cn.sichu.system.model.resp.DashboardAccessTrendResp;
import cn.sichu.system.model.resp.DashboardPopularModuleResp;
import cn.sichu.system.model.resp.DashboardTotalResp;
import cn.sichu.system.model.resp.log.LogDetailResp;
import cn.sichu.system.model.resp.log.LogResp;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;

/**
 * 系统日志业务接口
 *
 * @author sichu huang
 * @date 2024/10/10
 **/
public interface ILogService {

    /**
     * 分页查询列表
     *
     * @param query     查询条件
     * @param pageQuery 分页查询条件
     * @return cn.sichu.crud.mp.model.resp.PageResp<cn.sichu.system.model.resp.log.LogResp> 分页列表信息
     * @author sichu huang
     * @date 2024/10/12
     **/
    PageResp<LogResp> page(LogQuery query, PageQuery pageQuery);

    /**
     * 查询详情
     *
     * @param id ID
     * @return cn.sichu.system.model.resp.log.LogDetailResp 详情信息
     * @author sichu huang
     * @date 2024/10/12
     **/
    LogDetailResp get(Long id);

    /**
     * 导出登录日志
     *
     * @param query     查询条件
     * @param sortQuery 排序查询条件
     * @param response  响应对象
     * @author sichu huang
     * @date 2024/10/12
     **/
    void exportLoginLog(LogQuery query, SortQuery sortQuery, HttpServletResponse response);

    /**
     * 导出操作日志
     *
     * @param query     查询条件
     * @param sortQuery 排序查询条件
     * @param response  响应对象
     * @author sichu huang
     * @date 2024/10/12
     **/
    void exportOperationLog(LogQuery query, SortQuery sortQuery, HttpServletResponse response);

    /**
     * 查询仪表盘总计信息
     *
     * @return cn.sichu.system.model.resp.DashboardTotalResp 仪表盘总计信息
     * @author sichu huang
     * @date 2024/10/12
     **/
    DashboardTotalResp getDashboardTotal();

    /**
     * 查询仪表盘访问趋势信息
     *
     * @param days 日期数
     * @return java.util.List<cn.sichu.system.model.resp.DashboardAccessTrendResp> 仪表盘访问趋势信息
     * @author sichu huang
     * @date 2024/10/12
     **/
    List<DashboardAccessTrendResp> listDashboardAccessTrend(Integer days);

    /**
     * 查询仪表盘热门模块列表
     *
     * @return java.util.List<cn.sichu.system.model.resp.DashboardPopularModuleResp> 仪表盘热门模块列表
     * @author sichu huang
     * @date 2024/10/12
     **/
    List<DashboardPopularModuleResp> listDashboardPopularModule();

    /**
     * 查询仪表盘访客地域分布信息
     *
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>> 仪表盘访客地域分布信息
     * @author sichu huang
     * @date 2024/10/12
     **/
    List<Map<String, Object>> listDashboardGeoDistribution();
}
