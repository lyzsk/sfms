package cn.sichu.system.mapper;

import cn.sichu.system.model.bo.VisitCount;
import cn.sichu.system.model.entity.Log;
import cn.sichu.system.model.query.LogPageQuery;
import cn.sichu.system.model.vo.LogPageVO;
import cn.sichu.system.model.vo.VisitStatsVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author sichu huang
 * @since 2024/10/16 23:19
 */
@Mapper
public interface LogMapper extends BaseMapper<Log> {

    /**
     * 获取日志分页列表
     *
     * @param page        page
     * @param queryParams queryParams
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<cn.sichu.model.vo.LogPageVO>
     * @author sichu huang
     * @since 2024/10/16 23:21:43
     */
    Page<LogPageVO> getLogPage(Page<LogPageVO> page, LogPageQuery queryParams);

    /**
     * 统计浏览数(PV)
     *
     * @param startDate 开始日期 YYYY-MM-DD HH:mm:ss.SSS
     * @param endDate   结束日期 YYYY-MM-DD HH:mm:ss.SSS
     * @return java.util.List<cn.sichu.model.bo.VisitCount>
     * @author sichu huang
     * @since 2024/10/16 23:21:31
     */
    List<VisitCount> getPvCounts(String startDate, String endDate);

    /**
     * 统计IP数
     *
     * @param startDate 开始日期 YYYY-MM-DD HH:mm:ss.SSS
     * @param endDate   结束日期 YYYY-MM-DD HH:mm:ss.SSS
     * @return java.util.List<cn.sichu.model.bo.VisitCount>
     * @author sichu huang
     * @since 2024/10/16 23:21:21
     */
    List<VisitCount> getIpCounts(String startDate, String endDate);

    /**
     * 获取浏览量(PV)统计数据
     *
     * @return cn.sichu.model.vo.VisitStatsVO
     * @author sichu huang
     * @since 2024/10/16 23:21:12
     */
    VisitStatsVO getPvStats();

    /**
     * 获取IP统计数据
     *
     * @return cn.sichu.model.vo.VisitStatsVO
     * @author sichu huang
     * @since 2024/10/16 23:21:05
     */
    VisitStatsVO getIpStats();
}