package cn.sichu.controller.common;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.util.StrUtil;
import cn.sichu.constant.CacheConstants;
import cn.sichu.core.autoconfigure.project.ProjectProperties;
import cn.sichu.core.utils.validate.ValidationUtils;
import cn.sichu.crud.core.model.query.SortQuery;
import cn.sichu.crud.core.model.resp.LabelValueResp;
import cn.sichu.log.annotation.Log;
import cn.sichu.system.model.query.DeptQuery;
import cn.sichu.system.model.query.MenuQuery;
import cn.sichu.system.model.query.OptionQuery;
import cn.sichu.system.model.query.RoleQuery;
import cn.sichu.system.model.resp.FileUploadResp;
import cn.sichu.system.service.*;
import com.alicp.jetcache.anno.Cached;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.dromara.x.file.storage.core.FileInfo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 公共 API
 *
 * @author sichu huang
 * @date 2024/10/13
 **/
@Tag(name = "公共 API")
@Log(ignore = true)
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/common")
public class CommonController {

    private final ProjectProperties projectProperties;
    private final IFileService fileService;
    private final IDeptService deptService;
    private final IMenuService menuService;
    private final IRoleService roleService;
    private final IDictItemService dictItemService;
    private final IOptionService optionService;

    @Operation(summary = "上传文件", description = "上传文件")
    @PostMapping("/file")
    public FileUploadResp upload(@NotNull(message = "文件不能为空") MultipartFile file) {
        // TODO 实际开发时请删除本条校验
        ValidationUtils.throwIf(projectProperties.isProduction(), "演示环境不支持上传文件");
        ValidationUtils.throwIf(file::isEmpty, "文件不能为空");
        FileInfo fileInfo = fileService.upload(file);
        return FileUploadResp.builder().url(fileInfo.getUrl()).build();
    }

    @Operation(summary = "查询部门树", description = "查询树结构的部门列表")
    @GetMapping("/tree/dept")
    public List<Tree<Long>> listDeptTree(DeptQuery query, SortQuery sortQuery) {
        return deptService.tree(query, sortQuery, true);
    }

    @Operation(summary = "查询菜单树", description = "查询树结构的菜单列表")
    @GetMapping("/tree/menu")
    public List<Tree<Long>> listMenuTree(MenuQuery query, SortQuery sortQuery) {
        return menuService.tree(query, sortQuery, true);
    }

    @Operation(summary = "查询角色字典", description = "查询角色字典列表")
    @GetMapping("/dict/role")
    public List<LabelValueResp> listRoleDict(RoleQuery query, SortQuery sortQuery) {
        return roleService.listDict(query, sortQuery);
    }

    @Operation(summary = "查询字典", description = "查询字典列表")
    @Parameter(name = "code", description = "字典编码", example = "notice_type",
        in = ParameterIn.PATH)
    @GetMapping("/dict/{code}")
    public List<LabelValueResp> listDict(@PathVariable String code) {
        return dictItemService.listByDictCode(code);
    }

    @SaIgnore
    @Operation(summary = "查询参数字典", description = "查询参数字典")
    @GetMapping("/dict/option")
    @Cached(key = "#category", name = CacheConstants.OPTION_KEY_PREFIX)
    public List<LabelValueResp<String>> listOptionDict(
        @NotBlank(message = "类别不能为空") String category) {
        OptionQuery optionQuery = new OptionQuery();
        optionQuery.setCategory(category);
        return optionService.list(optionQuery).stream().map(
            option -> new LabelValueResp<>(option.getCode(),
                StrUtil.nullToDefault(option.getValue(), option.getDefaultValue()))).toList();
    }
}
