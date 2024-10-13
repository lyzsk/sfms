package cn.sichu.controller.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.sichu.crud.core.model.query.PageQuery;
import cn.sichu.crud.core.model.query.SortQuery;
import cn.sichu.crud.mp.model.resp.PageResp;
import cn.sichu.system.model.query.LogQuery;
import cn.sichu.system.model.resp.log.LogDetailResp;
import cn.sichu.system.model.resp.log.LogResp;
import cn.sichu.system.service.ILogService;
import com.feiniaojin.gracefulresponse.api.ExcludeFromGracefulResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统日志 API
 *
 * @author sichu huang
 * @date 2024/10/13
 **/
@Tag(name = "系统日志 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/log")
public class LogController {

    private final ILogService baseService;

    @Operation(summary = "分页查询列表", description = "分页查询列表")
    @SaCheckPermission("monitor:log:list")
    @GetMapping
    public PageResp<LogResp> page(LogQuery query, @Validated PageQuery pageQuery) {
        return baseService.page(query, pageQuery);
    }

    @Operation(summary = "查询详情", description = "查询详情")
    @Parameter(name = "id", description = "ID", example = "1", in = ParameterIn.PATH)
    @SaCheckPermission("monitor:log:list")
    @GetMapping("/{id}")
    public LogDetailResp get(@PathVariable Long id) {
        return baseService.get(id);
    }

    @ExcludeFromGracefulResponse
    @Operation(summary = "导出登录日志", description = "导出登录日志")
    @SaCheckPermission("monitor:log:export")
    @GetMapping("/export/login")
    public void exportLoginLog(LogQuery query, SortQuery sortQuery, HttpServletResponse response) {
        baseService.exportLoginLog(query, sortQuery, response);
    }

    @ExcludeFromGracefulResponse
    @Operation(summary = "导出操作日志", description = "导出操作日志")
    @SaCheckPermission("monitor:log:export")
    @GetMapping("/export/operation")
    public void exportOperationLog(LogQuery query, SortQuery sortQuery,
        HttpServletResponse response) {
        baseService.exportOperationLog(query, sortQuery, response);
    }
}
