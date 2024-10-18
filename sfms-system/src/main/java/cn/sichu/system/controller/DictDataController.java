package cn.sichu.system.controller;

import cn.sichu.annotation.Log;
import cn.sichu.annotation.RepeatSubmit;
import cn.sichu.enums.LogModuleEnum;
import cn.sichu.model.Option;
import cn.sichu.result.PageResult;
import cn.sichu.result.Result;
import cn.sichu.system.model.form.DictDataForm;
import cn.sichu.system.model.query.DictDataPageQuery;
import cn.sichu.system.model.vo.DictDataPageVO;
import cn.sichu.system.service.DictDataService;
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
 * @since 2024/10/17 16:58
 */
@Tag(name = "08.字典数据接口")
@RestController
@RequestMapping("/api/v1/dict-data")
@RequiredArgsConstructor
public class DictDataController {

    private final DictDataService dictDataService;

    @Operation(summary = "字典数据分页列表")
    @GetMapping("/page")
    @Log(value = "字典数据分页列表", module = LogModuleEnum.DICT)
    public PageResult<DictDataPageVO> getDictDataPage(DictDataPageQuery queryParams) {
        Page<DictDataPageVO> result = dictDataService.getDictDataPage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "字典数据表单内")
    @GetMapping("/{id}/form")
    public Result<DictDataForm> getDictDataForm(
        @Parameter(description = "字典数据ID") @PathVariable Long id) {
        DictDataForm formData = dictDataService.getDictDataForm(id);
        return Result.success(formData);
    }

    @Operation(summary = "新增字典数据")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('sys:dict-data:add')")
    @RepeatSubmit
    public Result<Void> saveDictData(@Valid @RequestBody DictDataForm formData) {
        boolean result = dictDataService.saveDictData(formData);
        return Result.judge(result);
    }

    @Operation(summary = "修改字典数据")
    @PutMapping("/{id}")
    @PreAuthorize("@ss.hasPerm('sys:dict-data:edit')")
    public Result<?> updateDictData(@PathVariable Long id, @RequestBody DictDataForm formData) {
        boolean status = dictDataService.updateDictData(formData);
        return Result.judge(status);
    }

    @Operation(summary = "删除字典数据")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('sys:dict-data:delete')")
    public Result<Void> deleteDictionaries(
        @Parameter(description = "字典ID，多个以英文逗号(,)拼接") @PathVariable String ids) {
        dictDataService.deleteDictDataByIds(ids);
        return Result.success();
    }

    @Operation(summary = "字典数据列表")
    @GetMapping("/{dictCode}/options")
    public Result<List<Option<String>>> getDictDataList(
        @Parameter(description = "字典编码") @PathVariable String dictCode) {
        List<Option<String>> options = dictDataService.getDictDataList(dictCode);
        return Result.success(options);
    }
}
