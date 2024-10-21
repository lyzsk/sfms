package cn.sichu.system.controller;

import cn.sichu.result.PageResult;
import cn.sichu.result.Result;
import cn.sichu.system.model.query.LogPageQuery;
import cn.sichu.system.model.vo.LogPageVO;
import cn.sichu.system.model.vo.VisitStatsVO;
import cn.sichu.system.model.vo.VisitTrendVO;
import cn.sichu.system.service.LogService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

/**
 * @author sichu huang
 * @since 2024/10/17 16:59
 */
@Tag(name = "09.日志接口")
@RestController
@RequestMapping("/api/v1/logs")
@RequiredArgsConstructor
public class LogController {

    private final LogService logService;

    @Operation(summary = "日志分页列表")
    @GetMapping("/page")
    public PageResult<LogPageVO> getLogPage(LogPageQuery queryParams) {
        Page<LogPageVO> result = logService.getLogPage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "获取访问趋势")
    @GetMapping("/visit-trend")
    public Result<VisitTrendVO> getVisitTrend(
        @Parameter(description = "开始时间", example = "YYYY-MM-DD") @RequestParam String startDate,
        @Parameter(description = "结束时间", example = "YYYY-MM-DD") @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        VisitTrendVO data = logService.getVisitTrend(start, end);
        return Result.success(data);
    }

    @Operation(summary = "获取统计数据")
    @GetMapping("/visit-stats")
    public Result<List<VisitStatsVO>> getVisitStats() {
        List<VisitStatsVO> list = logService.getVisitStats();
        return Result.success(list);
    }
}
