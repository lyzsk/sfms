package cn.sichu.system.mapper;

import cn.sichu.data.mp.base.BaseMapper;
import cn.sichu.system.model.entity.LogDO;
import cn.sichu.system.model.resp.DashboardAccessTrendResp;
import cn.sichu.system.model.resp.DashboardPopularModuleResp;
import cn.sichu.system.model.resp.DashboardTotalResp;
import cn.sichu.system.model.resp.log.LogResp;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 系统日志 Mapper
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public interface LogMapper extends BaseMapper<LogDO> {

    /**
     * 分页查询列表
     *
     * @param page         分页条件
     * @param queryWrapper 查询条件
     * @return com.baomidou.mybatisplus.core.metadata.IPage<cn.sichu.system.model.resp.log.LogResp> 分页列表信息
     * @author sichu huang
     * @date 2024/10/12
     **/
    IPage<LogResp> selectLogPage(@Param("page") IPage<LogDO> page,
        @Param(Constants.WRAPPER) QueryWrapper<LogDO> queryWrapper);

    /**
     * 查询列表
     *
     * @param queryWrapper 查询条件
     * @return java.util.List<cn.sichu.system.model.resp.log.LogResp> 列表信息
     * @author sichu huang
     * @date 2024/10/12
     **/
    List<LogResp> selectLogList(@Param(Constants.WRAPPER) QueryWrapper<LogDO> queryWrapper);

    /**
     * 查询仪表盘总计信息
     *
     * @return cn.sichu.system.model.resp.DashboardTotalResp 仪表盘总计信息
     * @author sichu huang
     * @date 2024/10/12
     **/
    DashboardTotalResp selectDashboardTotal();

    /**
     * 查询仪表盘访问趋势信息
     *
     * @param days 日期数
     * @return java.util.List<cn.sichu.system.model.resp.DashboardAccessTrendResp> 仪表盘访问趋势信息
     * @author sichu huang
     * @date 2024/10/12
     **/
    List<DashboardAccessTrendResp> selectListDashboardAccessTrend(@Param("days") Integer days);

    /**
     * 查询仪表盘热门模块列表
     *
     * @return java.util.List<cn.sichu.system.model.resp.DashboardPopularModuleResp> 仪表盘热门模块列表
     * @author sichu huang
     * @date 2024/10/12
     **/
    List<DashboardPopularModuleResp> selectListDashboardPopularModule();

    /**
     * 查询仪表盘访客地域分布信息
     *
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>> 仪表盘访客地域分布信息
     * @author sichu huang
     * @date 2024/10/12
     **/
    @MapKey("name")
    List<Map<String, Object>> selectListDashboardGeoDistribution();
}
