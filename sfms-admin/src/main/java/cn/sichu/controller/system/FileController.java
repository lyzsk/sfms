package cn.sichu.controller.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.sichu.crud.core.annotation.CrudRequestMapping;
import cn.sichu.crud.core.enums.Api;
import cn.sichu.crud.mp.controller.BaseController;
import cn.sichu.log.annotation.Log;
import cn.sichu.system.model.query.FileQuery;
import cn.sichu.system.model.req.FileReq;
import cn.sichu.system.model.resp.FileResp;
import cn.sichu.system.model.resp.FileStatisticsResp;
import cn.sichu.system.service.IFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文件管理 API
 *
 * @author sichu huang
 * @date 2024/10/13
 **/
@Tag(name = "文件管理 API")
@RestController
@RequiredArgsConstructor
@CrudRequestMapping(value = "/system/file", api = {Api.PAGE, Api.UPDATE, Api.DELETE})
public class FileController
    extends BaseController<IFileService, FileResp, FileResp, FileQuery, FileReq> {

    @Log(ignore = true)
    @Operation(summary = "查询文件资源统计", description = "查询文件资源统计")
    @SaCheckPermission("system:file:list")
    @GetMapping("/statistics")
    public FileStatisticsResp statistics() {
        return baseService.statistics();
    }
}