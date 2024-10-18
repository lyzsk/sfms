package cn.sichu.system.controller;

import cn.sichu.result.PageResult;
import cn.sichu.result.Result;
import cn.sichu.system.model.form.ConfigForm;
import cn.sichu.system.model.query.ConfigPageQuery;
import cn.sichu.system.model.vo.ConfigVO;
import cn.sichu.system.service.ConfigService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author sichu huang
 * @since 2024/10/17 16:55
 */
@Tag(name = "10.系统配置")
@Slf4j
@RestController
@RequestMapping("/api/v1/config")
@RequiredArgsConstructor
public class ConfigController {

    private final ConfigService configService;

    @GetMapping("/page")
    @Operation(summary = "系统配置分页列表")
    @PreAuthorize("@ss.hasPerm('sys:config:query')")
    public PageResult<ConfigVO> page(@ParameterObject ConfigPageQuery configPageQuery) {
        IPage<ConfigVO> result = configService.page(configPageQuery);
        return PageResult.success(result);
    }

    @Operation(summary = "新增系统配置")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('sys:config:add')")
    public Result<?> save(@RequestBody @Valid ConfigForm configForm) {
        return Result.judge(configService.save(configForm));
    }

    @Operation(summary = "获取系统配置表单数据")
    @GetMapping("/{id}/form")
    public Result<ConfigForm> getConfigForm(
        @Parameter(description = "系统配置ID") @PathVariable Long id) {
        ConfigForm formData = configService.getConfigFormData(id);
        return Result.success(formData);
    }

    @Operation(summary = "刷新系统配置缓存")
    @PatchMapping
    @PreAuthorize("@ss.hasPerm('sys:config:refresh')")
    public Result<ConfigForm> refreshCache() {
        return Result.judge(configService.refreshCache());
    }

    @PutMapping(value = "/{id}")
    @Operation(summary = "修改系统配置")
    @PreAuthorize("@ss.hasPerm('sys:config:update')")
    public Result<?> update(@Valid @PathVariable Long id, @RequestBody ConfigForm configForm) {
        return Result.judge(configService.edit(id, configForm));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除系统配置")
    @PreAuthorize("@ss.hasPerm('sys:config:delete')")
    public Result<?> delete(@PathVariable Long id) {
        return Result.judge(configService.delete(id));
    }
}
