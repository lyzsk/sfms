package cn.sichu.system.controller;

import cn.sichu.annotation.Log;
import cn.sichu.annotation.RepeatSubmit;
import cn.sichu.enums.LogModuleEnum;
import cn.sichu.model.Option;
import cn.sichu.result.Result;
import cn.sichu.system.core.utils.SecurityUtils;
import cn.sichu.system.model.form.MenuForm;
import cn.sichu.system.model.query.MenuQuery;
import cn.sichu.system.model.vo.MenuVO;
import cn.sichu.system.model.vo.RouteVO;
import cn.sichu.system.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * @author sichu huang
 * @since 2024/10/17 16:59
 */
@Slf4j
@Tag(name = "04.菜单接口")
@RestController
@RequestMapping("/api/v1/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @Operation(summary = "菜单列表")
    @GetMapping
    @Log(value = "菜单列表", module = LogModuleEnum.MENU)
    public Result<List<MenuVO>> listMenus(MenuQuery queryParams) {
        List<MenuVO> menuList = menuService.listMenus(queryParams);
        return Result.success(menuList);
    }

    @Operation(summary = "菜单下拉列表")
    @GetMapping("/options")
    public Result<?> listMenuOptions(@Parameter(description = "是否只查询父级菜单")
    @RequestParam(required = false, defaultValue = "false") boolean onlyParent) {
        List<Option> menus = menuService.listMenuOptions(onlyParent);
        return Result.success(menus);
    }

    @Operation(summary = "菜单路由列表")
    @GetMapping("/routes")
    public Result<List<RouteVO>> listRoutes() {
        Set<String> roles = SecurityUtils.getRoles();
        List<RouteVO> routeList = menuService.listRoutes(roles);
        return Result.success(routeList);
    }

    @Operation(summary = "菜单表单数据")
    @GetMapping("/{id}/form")
    public Result<MenuForm> getMenuForm(@Parameter(description = "菜单ID") @PathVariable Long id) {
        MenuForm menu = menuService.getMenuForm(id);
        return Result.success(menu);
    }

    @Operation(summary = "新增菜单")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('sys:menu:add')")
    @RepeatSubmit
    public Result<?> addMenu(@RequestBody MenuForm menuForm) {
        boolean result = menuService.saveMenu(menuForm);
        return Result.judge(result);
    }

    @Operation(summary = "修改菜单")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@ss.hasPerm('sys:menu:edit')")
    public Result<?> updateMenu(@RequestBody MenuForm menuForm) {
        boolean result = menuService.saveMenu(menuForm);
        return Result.judge(result);
    }

    @Operation(summary = "删除菜单")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ss.hasPerm('sys:menu:delete')")
    public Result<?> deleteMenu(
        @Parameter(description = "菜单ID，多个以英文(,)分割") @PathVariable("id") Long id) {
        boolean result = menuService.deleteMenu(id);
        return Result.judge(result);
    }

    @Operation(summary = "修改菜单显示状态")
    @PatchMapping("/{menuId}")
    public Result<?> updateMenuVisible(@Parameter(description = "菜单ID") @PathVariable Long menuId,
        @Parameter(description = "显示状态(1:显示;0:隐藏)") Integer visible

    ) {
        boolean result = menuService.updateMenuVisible(menuId, visible);
        return Result.judge(result);
    }
}
