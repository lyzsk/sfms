package cn.sichu.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.sichu.constant.SystemConstants;
import cn.sichu.enums.StatusEnum;
import cn.sichu.model.KeyValue;
import cn.sichu.model.Option;
import cn.sichu.system.converter.MenuConverter;
import cn.sichu.system.enums.MenuTypeEnum;
import cn.sichu.system.mapper.MenuMapper;
import cn.sichu.system.model.bo.RouteBO;
import cn.sichu.system.model.entity.Menu;
import cn.sichu.system.model.form.MenuForm;
import cn.sichu.system.model.query.MenuQuery;
import cn.sichu.system.model.vo.MenuVO;
import cn.sichu.system.model.vo.RouteVO;
import cn.sichu.system.service.MenuService;
import cn.sichu.system.service.RoleMenuService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author sichu huang
 * @since 2024/10/17 15:57
 */
@Service
@RequiredArgsConstructor
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    private final MenuConverter menuConverter;
    private final RoleMenuService roleMenuService;

    @Override
    public List<MenuVO> listMenus(MenuQuery queryParams) {
        List<Menu> menus = this.list(
            new LambdaQueryWrapper<Menu>().like(StrUtil.isNotBlank(queryParams.getKeywords()),
                Menu::getName, queryParams.getKeywords()).orderByAsc(Menu::getSort));
        // 获取所有菜单ID
        Set<Long> menuIds = menus.stream().map(Menu::getId).collect(Collectors.toSet());

        // 获取所有父级ID
        Set<Long> parentIds = menus.stream().map(Menu::getParentId).collect(Collectors.toSet());

        // 获取根节点ID（递归的起点），即父节点ID中不包含在部门ID中的节点，注意这里不能拿顶级菜单 O 作为根节点，因为菜单筛选的时候 O 会被过滤掉
        List<Long> rootIds = parentIds.stream().filter(id -> !menuIds.contains(id)).toList();

        // 使用递归函数来构建菜单树
        return rootIds.stream().flatMap(rootId -> buildMenuTree(rootId, menus).stream())
            .collect(Collectors.toList());
    }

    /**
     * 递归生成菜单列表
     *
     * @param parentId 父级ID
     * @param menuList 菜单列表
     * @return java.util.List<cn.sichu.model.vo.MenuVO> 菜单列表
     * @author sichu huang
     * @since 2024/10/17 15:58:35
     */
    private List<MenuVO> buildMenuTree(Long parentId, List<Menu> menuList) {
        return CollectionUtil.emptyIfNull(menuList).stream()
            .filter(menu -> menu.getParentId().equals(parentId)).map(entity -> {
                MenuVO menuVO = menuConverter.toVo(entity);
                List<MenuVO> children = buildMenuTree(entity.getId(), menuList);
                menuVO.setChildren(children);
                return menuVO;
            }).toList();
    }

    @Override
    public List<Option> listMenuOptions(boolean onlyParent) {
        List<Menu> menuList = this.list(new LambdaQueryWrapper<Menu>().in(onlyParent, Menu::getType,
                MenuTypeEnum.CATALOG.getValue(), MenuTypeEnum.MENU.getValue())
            .orderByAsc(Menu::getSort));
        return buildMenuOptions(SystemConstants.ROOT_NODE_ID, menuList);
    }

    /**
     * 递归生成菜单下拉层级列表
     *
     * @param parentId 父级ID
     * @param menuList 菜单列表
     * @return java.util.List<cn.sichu.model.Option> 菜单下拉列表
     * @author sichu huang
     * @since 2024/10/17 15:59:06
     */
    private List<Option> buildMenuOptions(Long parentId, List<Menu> menuList) {
        List<Option> menuOptions = new ArrayList<>();

        for (Menu menu : menuList) {
            if (menu.getParentId().equals(parentId)) {
                Option option = new Option(menu.getId(), menu.getName());
                List<Option> subMenuOptions = buildMenuOptions(menu.getId(), menuList);
                if (!subMenuOptions.isEmpty()) {
                    option.setChildren(subMenuOptions);
                }
                menuOptions.add(option);
            }
        }

        return menuOptions;
    }

    @Override
    public List<RouteVO> listRoutes(Set<String> roles) {

        if (CollectionUtil.isEmpty(roles)) {
            return Collections.emptyList();
        }

        List<RouteBO> menuList = this.baseMapper.listRoutes(roles);
        return buildRoutes(SystemConstants.ROOT_NODE_ID, menuList);
    }

    /**
     * 递归生成菜单路由层级列表
     *
     * @param parentId 父级ID
     * @param menuList 菜单列表
     * @return java.util.List<cn.sichu.model.vo.RouteVO> 路由层级列表
     * @author sichu huang
     * @since 2024/10/17 15:59:28
     */
    private List<RouteVO> buildRoutes(Long parentId, List<RouteBO> menuList) {
        List<RouteVO> routeList = new ArrayList<>();

        for (RouteBO menu : menuList) {
            if (menu.getParentId().equals(parentId)) {
                RouteVO routeVO = toRouteVo(menu);
                List<RouteVO> children = buildRoutes(menu.getId(), menuList);
                if (!children.isEmpty()) {
                    routeVO.setChildren(children);
                }
                routeList.add(routeVO);
            }
        }
        return routeList;
    }

    /**
     * 根据RouteBO创建RouteVO
     *
     * @param routeBO routeBO
     * @return cn.sichu.model.vo.RouteVO
     * @author sichu huang
     * @since 2024/10/17 15:59:41
     */
    private RouteVO toRouteVo(RouteBO routeBO) {
        RouteVO routeVO = new RouteVO();
        // 获取路由名称
        String routeName = routeBO.getRouteName();
        if (StrUtil.isBlank(routeName)) {
            // 路由 name 需要驼峰，首字母大写
            routeName = StringUtils.capitalize(StrUtil.toCamelCase(routeBO.getRoutePath(), '-'));
        }
        // 根据name路由跳转 this.$router.push({name:xxx})
        routeVO.setName(routeName);

        // 根据path路由跳转 this.$router.push({path:xxx})
        routeVO.setPath(routeBO.getRoutePath());
        routeVO.setRedirect(routeBO.getRedirect());
        routeVO.setComponent(routeBO.getComponent());

        RouteVO.Meta meta = new RouteVO.Meta();
        meta.setTitle(routeBO.getName());
        meta.setIcon(routeBO.getIcon());
        meta.setHidden(StatusEnum.DISABLE.getValue().equals(routeBO.getVisible()));
        // 【菜单】是否开启页面缓存
        if (MenuTypeEnum.MENU.equals(routeBO.getType()) && ObjectUtil.equals(routeBO.getKeepAlive(),
            1)) {
            meta.setKeepAlive(true);
        }
        meta.setAlwaysShow(ObjectUtil.equals(routeBO.getAlwaysShow(), 1));

        String paramsJson = routeBO.getParams();
        // 将 JSON 字符串转换为 Map<String, String>
        if (StrUtil.isNotBlank(paramsJson)) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                Map<String, String> paramMap =
                    objectMapper.readValue(paramsJson, new TypeReference<>() {
                    });
                meta.setParams(paramMap);
            } catch (Exception e) {
                throw new RuntimeException("解析参数失败", e);
            }
        }
        routeVO.setMeta(meta);
        return routeVO;
    }

    @Override
    @CacheEvict(cacheNames = "menu", key = "'routes'")
    public boolean saveMenu(MenuForm menuForm) {
        MenuTypeEnum menuType = menuForm.getType();
        if (menuType == MenuTypeEnum.CATALOG) {  // 如果是外链
            String path = menuForm.getRoutePath();
            if (menuForm.getParentId() == 0 && !path.startsWith("/")) {
                menuForm.setRoutePath("/" + path); // 一级目录需以 / 开头
            }
            menuForm.setComponent("Layout");
        } else if (menuType == MenuTypeEnum.EXTLINK) {   // 如果是目录

            menuForm.setComponent(null);
        }
        Menu entity = menuConverter.toEntity(menuForm);
        String treePath = generateMenuTreePath(menuForm.getParentId());
        entity.setTreePath(treePath);
        List<KeyValue> params = menuForm.getParams();
        // 路由参数 [{key:"id",value:"1"}，{key:"name",value:"张三"}] 转换为 [{"id":"1"},{"name":"张三"}]
        if (CollectionUtil.isNotEmpty(params)) {
            entity.setParams(JSONUtil.toJsonStr(
                params.stream().collect(Collectors.toMap(KeyValue::getKey, KeyValue::getValue))));
        } else {
            entity.setParams(null);
        }
        // 新增类型为菜单时候 路由名称唯一
        if (MenuTypeEnum.MENU.equals(menuType)) {
            Assert.isFalse(this.exists(
                    new LambdaQueryWrapper<Menu>().eq(Menu::getRouteName, entity.getRouteName())
                        .ne(menuForm.getId() != null, Menu::getId, menuForm.getId())),
                "路由名称已存在");
        } else {
            // 其他类型时 给路由名称赋值为空
            entity.setRouteName(null);
        }
        boolean result = this.saveOrUpdate(entity);
        if (result) {
            // 编辑刷新角色权限缓存
            if (menuForm.getId() != null) {
                roleMenuService.refreshRolePermsCache();
            }
        }
        // 修改菜单如果有子菜单，则更新子菜单的树路径
        updateChildrenTreePath(entity.getId(), treePath);
        return result;
    }

    /**
     * 更新子菜单树路径
     *
     * @param id       当前菜单ID
     * @param treePath 当前菜单树路径
     * @author sichu huang
     * @since 2024/10/17 16:00:26
     */
    private void updateChildrenTreePath(Long id, String treePath) {
        List<Menu> children = this.list(new LambdaQueryWrapper<Menu>().eq(Menu::getParentId, id));
        if (CollectionUtil.isNotEmpty(children)) {
            // 子菜单的树路径等于父菜单的树路径加上父菜单ID
            String childTreePath = treePath + "," + id;
            this.update(new LambdaUpdateWrapper<Menu>().eq(Menu::getParentId, id)
                .set(Menu::getTreePath, childTreePath));
            for (Menu child : children) {
                // 递归更新子菜单
                updateChildrenTreePath(child.getId(), childTreePath);
            }
        }
    }

    /**
     * 部门路径生成
     *
     * @param parentId 父ID
     * @return java.lang.String 父节点路径以英文逗号(, )分割，eg: 1,2,3
     * @author sichu huang
     * @since 2024/10/17 16:00:37
     */
    private String generateMenuTreePath(Long parentId) {
        if (SystemConstants.ROOT_NODE_ID.equals(parentId)) {
            return String.valueOf(parentId);
        } else {
            Menu parent = this.getById(parentId);
            return parent != null ? parent.getTreePath() + "," + parent.getId() : null;
        }
    }

    @Override
    @CacheEvict(cacheNames = "menu", key = "'routes'")
    public boolean updateMenuVisible(Long menuId, Integer visible) {
        return this.update(
            new LambdaUpdateWrapper<Menu>().eq(Menu::getId, menuId).set(Menu::getVisible, visible));
    }

    @Override
    public MenuForm getMenuForm(Long id) {
        Menu entity = this.getById(id);
        Assert.isTrue(entity != null, "菜单不存在");
        MenuForm formData = menuConverter.toForm(entity);
        // 路由参数字符串 {"id":"1","name":"张三"} 转换为 [{key:"id", value:"1"}, {key:"name", value:"张三"}]
        String params = entity.getParams();
        if (StrUtil.isNotBlank(params)) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                // 解析 JSON 字符串为 Map<String, String>
                Map<String, String> paramMap =
                    objectMapper.readValue(params, new TypeReference<>() {
                    });

                // 转换为 List<KeyValue> 格式 [{key:"id", value:"1"}, {key:"name", value:"张三"}]
                List<KeyValue> transformedList = paramMap.entrySet().stream()
                    .map(entry -> new KeyValue(entry.getKey(), entry.getValue())).toList();

                // 将转换后的列表存入 MenuForm
                formData.setParams(transformedList);
            } catch (Exception e) {
                throw new RuntimeException("解析参数失败", e);
            }
        }

        return formData;
    }

    @Override
    @CacheEvict(cacheNames = "menu", key = "'routes'")
    public boolean deleteMenu(Long id) {
        boolean result = this.remove(new LambdaQueryWrapper<Menu>().eq(Menu::getId, id).or()
            .apply("CONCAT (',',tree_path,',') LIKE CONCAT('%,',{0},',%')", id));

        // 刷新角色权限缓存
        if (result) {
            roleMenuService.refreshRolePermsCache();
        }
        return result;

    }

}
