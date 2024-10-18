package cn.sichu.system.service;

import cn.sichu.system.model.entity.Log;
import cn.sichu.system.model.query.LogPageQuery;
import cn.sichu.system.model.vo.LogPageVO;
import cn.sichu.system.model.vo.VisitStatsVO;
import cn.sichu.system.model.vo.VisitTrendVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDate;
import java.util.List;

/**
 * 系统日志 服务接口
 *
 * @author sichu huang
 * @since 2024/10/16 23:55:34
 */
public interface LogService extends IService<Log> {

    /**
     * 获取日志分页列表
     *
     * @param queryParams 查询参数
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<cn.sichu.model.vo.LogPageVO>
     * @author sichu huang
     * @since 2024/10/16 23:55:57
     */
    Page<LogPageVO> getLogPage(LogPageQuery queryParams);

    /**
     * 获取访问趋势
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return cn.sichu.model.vo.VisitTrendVO
     * @author sichu huang
     * @since 2024/10/16 23:56:08
     */
    VisitTrendVO getVisitTrend(LocalDate startDate, LocalDate endDate);

    /**
     * 获取访问统计
     *
     * @return java.util.List<cn.sichu.model.vo.VisitStatsVO>
     * @author sichu huang
     * @since 2024/10/16 23:56:21
     */
    List<VisitStatsVO> getVisitStats();

}
