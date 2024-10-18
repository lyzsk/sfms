package cn.sichu.system.controller;

import cn.sichu.annotation.Log;
import cn.sichu.annotation.RepeatSubmit;
import cn.sichu.enums.LogModuleEnum;
import cn.sichu.model.Option;
import cn.sichu.result.Result;
import cn.sichu.system.model.form.DeptForm;
import cn.sichu.system.model.query.DeptQuery;
import cn.sichu.system.model.vo.DeptVO;
import cn.sichu.system.service.DeptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author sichu huang
 * @since 2024/10/17 16:56
 */
@Tag(name = "05.部门接口")
@RestController
@RequestMapping("/api/v1/dept")
@RequiredArgsConstructor
public class DeptController {
    private final DeptService deptService;

    @Operation(summary = "部门列表")
    @GetMapping
    @Log(value = "部门列表", module = LogModuleEnum.DEPT)
    public Result<List<DeptVO>> getDeptList(DeptQuery queryParams) {
        List<DeptVO> list = deptService.getDeptList(queryParams);
        return Result.success(list);
    }

    @Operation(summary = "部门下拉列表")
    @GetMapping("/options")
    public Result<List<Option<Long>>> getDeptOptions() {
        List<Option<Long>> list = deptService.listDeptOptions();
        return Result.success(list);
    }

    @Operation(summary = "新增部门")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('sys:dept:add')")
    @RepeatSubmit
    public Result<?> saveDept(@Valid @RequestBody DeptForm formData) {
        Long id = deptService.saveDept(formData);
        return Result.success(id);
    }

    @Operation(summary = "获取部门表单数据")
    @GetMapping("/{deptId}/form")
    public Result<DeptForm> getDeptForm(
        @Parameter(description = "部门ID") @PathVariable Long deptId) {
        DeptForm deptForm = deptService.getDeptForm(deptId);
        return Result.success(deptForm);
    }

    @Operation(summary = "修改部门")
    @PutMapping(value = "/{deptId}")
    @PreAuthorize("@ss.hasPerm('sys:dept:edit')")
    public Result<?> updateDept(@PathVariable Long deptId, @Valid @RequestBody DeptForm formData) {
        deptId = deptService.updateDept(deptId, formData);
        return Result.success(deptId);
    }

    @Operation(summary = "删除部门")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('sys:dept:delete')")
    public Result<?> deleteDepartments(
        @Parameter(description = "部门ID，多个以英文逗号(,)分割") @PathVariable("ids") String ids) {
        boolean result = deptService.deleteByIds(ids);
        return Result.judge(result);
    }
}
