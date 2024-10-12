package cn.sichu.system.service.impl;

import cn.crane4j.annotation.AutoOperate;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import cn.sichu.core.utils.validate.CheckUtils;
import cn.sichu.crud.core.model.query.PageQuery;
import cn.sichu.crud.core.model.query.SortQuery;
import cn.sichu.crud.mp.model.resp.PageResp;
import cn.sichu.enums.DisEnableStatusEnum;
import cn.sichu.file.excel.utils.ExcelUtils;
import cn.sichu.system.mapper.LogMapper;
import cn.sichu.system.model.entity.LogDO;
import cn.sichu.system.model.query.LogQuery;
import cn.sichu.system.model.resp.DashboardAccessTrendResp;
import cn.sichu.system.model.resp.DashboardPopularModuleResp;
import cn.sichu.system.model.resp.DashboardTotalResp;
import cn.sichu.system.model.resp.log.LogDetailResp;
import cn.sichu.system.model.resp.log.LogResp;
import cn.sichu.system.model.resp.log.LoginLogExportResp;
import cn.sichu.system.model.resp.log.OperationLogExportResp;
import cn.sichu.system.service.ILogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 系统日志业务实现
 *
 * @author sichu huang
 * @date 2024/10/10
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class LogServiceImpl implements ILogService {

    private final LogMapper baseMapper;

    @Override
    public PageResp<LogResp> page(LogQuery query, PageQuery pageQuery) {
        QueryWrapper<LogDO> queryWrapper = this.buildQueryWrapper(query);
        IPage<LogResp> page =
            baseMapper.selectLogPage(new Page<>(pageQuery.getPage(), pageQuery.getSize()),
                queryWrapper);
        return PageResp.build(page);
    }

    @Override
    @AutoOperate(type = LogDetailResp.class)
    public LogDetailResp get(Long id) {
        LogDO logDO = baseMapper.selectById(id);
        CheckUtils.throwIfNotExists(logDO, "LogDO", "ID", id);
        return BeanUtil.copyProperties(logDO, LogDetailResp.class);
    }

    @Override
    public void exportLoginLog(LogQuery query, SortQuery sortQuery, HttpServletResponse response) {
        List<LoginLogExportResp> list =
            BeanUtil.copyToList(this.list(query, sortQuery), LoginLogExportResp.class);
        ExcelUtils.export(list, "导出登录日志数据", LoginLogExportResp.class, response);
    }

    @Override
    public void exportOperationLog(LogQuery query, SortQuery sortQuery,
        HttpServletResponse response) {
        List<OperationLogExportResp> list =
            BeanUtil.copyToList(this.list(query, sortQuery), OperationLogExportResp.class);
        ExcelUtils.export(list, "导出操作日志数据", OperationLogExportResp.class, response);
    }

    @Override
    public DashboardTotalResp getDashboardTotal() {
        return baseMapper.selectDashboardTotal();
    }

    @Override
    public List<DashboardAccessTrendResp> listDashboardAccessTrend(Integer days) {
        return baseMapper.selectListDashboardAccessTrend(days);
    }

    @Override
    public List<DashboardPopularModuleResp> listDashboardPopularModule() {
        return baseMapper.selectListDashboardPopularModule();
    }

    @Override
    public List<Map<String, Object>> listDashboardGeoDistribution() {
        return baseMapper.selectListDashboardGeoDistribution();
    }

    /**
     * 查询列表
     *
     * @param query     查询条件
     * @param sortQuery 排序查询条件
     * @return 列表信息
     */
    private List<LogResp> list(LogQuery query, SortQuery sortQuery) {
        QueryWrapper<LogDO> queryWrapper = this.buildQueryWrapper(query);
        this.sort(queryWrapper, sortQuery);
        return baseMapper.selectLogList(queryWrapper);
    }

    /**
     * 设置排序
     *
     * @param queryWrapper 查询条件封装对象
     * @param sortQuery    排序查询条件
     */
    private void sort(QueryWrapper<LogDO> queryWrapper, SortQuery sortQuery) {
        if (sortQuery == null || sortQuery.getSort().isUnsorted()) {
            return;
        }
        for (Sort.Order order : sortQuery.getSort()) {
            String property = order.getProperty();
            queryWrapper.orderBy(true, order.isAscending(),
                CharSequenceUtil.toUnderlineCase(property));
        }
    }

    /**
     * 构建 QueryWrapper
     *
     * @param query 查询条件
     * @return QueryWrapper
     */
    private QueryWrapper<LogDO> buildQueryWrapper(LogQuery query) {
        String description = query.getDescription();
        String module = query.getModule();
        String ip = query.getIp();
        String createUserString = query.getCreateUserString();
        DisEnableStatusEnum status = query.getStatus();
        List<Date> createTimeList = query.getCreateTime();
        return new QueryWrapper<LogDO>().and(StrUtil.isNotBlank(description),
                q -> q.like("t1.description", description).or().like("t1.module", description))
            .eq(StrUtil.isNotBlank(module), "t1.module", module)
            .and(StrUtil.isNotBlank(ip), q -> q.like("t1.ip", ip).or().like("t1.address", ip))
            .and(StrUtil.isNotBlank(createUserString),
                q -> q.like("t2.username", createUserString).or()
                    .like("t2.nickname", createUserString)).eq(null != status, "t1.status", status)
            .between(CollUtil.isNotEmpty(createTimeList), "t1.create_time",
                CollUtil.getFirst(createTimeList), CollUtil.getLast(createTimeList));
    }
}
