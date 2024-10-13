package cn.sichu.controller.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.sichu.system.model.query.OptionQuery;
import cn.sichu.system.model.req.OptionReq;
import cn.sichu.system.model.req.OptionResetValueReq;
import cn.sichu.system.model.resp.OptionResp;
import cn.sichu.system.service.IOptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 参数管理 API
 *
 * @author sichu huang
 * @date 2024/10/13
 **/
@Tag(name = "参数管理 API")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/option")
public class OptionController {

    private final IOptionService baseService;

    @Operation(summary = "查询参数列表", description = "查询参数列表")
    @SaCheckPermission("system:config:list")
    @GetMapping
    public List<OptionResp> list(@Validated OptionQuery query) {
        return baseService.list(query);
    }

    @Operation(summary = "修改参数", description = "修改参数")
    @SaCheckPermission("system:config:update")
    @PutMapping
    public void update(@Valid @RequestBody List<OptionReq> options) {
        baseService.update(options);
    }

    @Operation(summary = "重置参数", description = "重置参数")
    @SaCheckPermission("system:config:reset")
    @PatchMapping("/value")
    public void resetValue(@Validated @RequestBody OptionResetValueReq req) {
        baseService.resetValue(req);
    }
}