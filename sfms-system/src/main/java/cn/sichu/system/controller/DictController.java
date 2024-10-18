package cn.sichu.system.controller;

import cn.sichu.annotation.Log;
import cn.sichu.annotation.RepeatSubmit;
import cn.sichu.enums.LogModuleEnum;
import cn.sichu.model.Option;
import cn.sichu.result.PageResult;
import cn.sichu.result.Result;
import cn.sichu.system.model.form.DictForm;
import cn.sichu.system.model.query.DictPageQuery;
import cn.sichu.system.model.vo.DictPageVO;
import cn.sichu.system.service.DictService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
 * @since 2024/10/17 16:57
 */
@Tag(name = "06.字典接口")
@RestController
@RequestMapping("/api/v1/dict")
@RequiredArgsConstructor
public class DictController {

    private final DictService dictService;

    @Operation(summary = "字典分页列表")
    @GetMapping("/page")
    @Log(value = "字典分页列表", module = LogModuleEnum.DICT)
    public PageResult<DictPageVO> getDictPage(DictPageQuery queryParams) {
        Page<DictPageVO> result = dictService.getDictPage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "字典列表")
    @GetMapping("/list")
    public Result<List<Option<String>>> getDictList() {
        List<Option<String>> list = dictService.getDictList();
        return Result.success(list);
    }

    @Operation(summary = "字典表单")
    @GetMapping("/{id}/form")
    public Result<DictForm> getDictForm(@Parameter(description = "字典ID") @PathVariable Long id) {
        DictForm formData = dictService.getDictForm(id);
        return Result.success(formData);
    }

    @Operation(summary = "新增字典")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('sys:dict:add')")
    @RepeatSubmit
    public Result<?> saveDict(@Valid @RequestBody DictForm formData) {
        boolean result = dictService.saveDict(formData);
        return Result.judge(result);
    }

    @Operation(summary = "修改字典")
    @PutMapping("/{id}")
    @PreAuthorize("@ss.hasPerm('sys:dict:edit')")
    public Result<?> updateDict(@PathVariable Long id, @RequestBody DictForm DictForm) {
        boolean status = dictService.updateDict(id, DictForm);
        return Result.judge(status);
    }

    @Operation(summary = "删除字典")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('sys:dict:delete')")
    public Result<?> deleteDictionaries(
        @Parameter(description = "字典ID，多个以英文逗号(,)拼接") @PathVariable String ids) {
        dictService.deleteDictByIds(ids);
        return Result.success();
    }
}
