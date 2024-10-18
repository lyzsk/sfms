package cn.sichu.system.service;

import cn.sichu.model.Option;
import cn.sichu.system.model.entity.Menu;
import cn.sichu.system.model.form.MenuForm;
import cn.sichu.system.model.query.MenuQuery;
import cn.sichu.system.model.vo.MenuVO;
import cn.sichu.system.model.vo.RouteVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * 菜单业务接口
 *
 * @author haoxr
 * @since 2020/11/06
 */
public interface MenuService extends IService<Menu> {

    /**
     * 获取菜单表格列表
     *
     * @param queryParams queryParams
     * @return java.util.List<cn.sichu.model.vo.MenuVO>
     * @author sichu huang
     * @since 2024/10/17 15:45:18
     */
    List<MenuVO> listMenus(MenuQuery queryParams);

    /**
     * 获取菜单下拉列表
     *
     * @param onlyParent 是否只查询父级菜单
     * @return java.util.List<cn.sichu.model.Option>
     * @author sichu huang
     * @since 2024/10/17 15:45:27
     */
    List<Option> listMenuOptions(boolean onlyParent);

    /**
     *
     *
     * @param menuForm
     */
    /**
     * 新增菜单
     *
     * @param menuForm 菜单表单对象
     * @return boolean
     * @author sichu huang
     * @since 2024/10/17 15:45:37
     */
    boolean saveMenu(MenuForm menuForm);

    /**
     * 获取路由列表
     *
     * @param roles roles
     * @return java.util.List<cn.sichu.model.vo.RouteVO>
     * @author sichu huang
     * @since 2024/10/17 15:45:45
     */
    List<RouteVO> listRoutes(Set<String> roles);

    /**
     * 修改菜单显示状态
     *
     * @param menuId  菜单ID
     * @param visible 是否显示(1-显示 0-隐藏)
     * @return boolean
     * @author sichu huang
     * @since 2024/10/17 15:45:55
     */
    boolean updateMenuVisible(Long menuId, Integer visible);

    /**
     * 获取菜单表单数据
     *
     * @param id 菜单ID
     * @return cn.sichu.model.form.MenuForm
     * @author sichu huang
     * @since 2024/10/17 15:46:05
     */
    MenuForm getMenuForm(Long id);

    /**
     * 删除菜单
     *
     * @param id 菜单ID
     * @return boolean
     * @author sichu huang
     * @since 2024/10/17 15:46:14
     */
    boolean deleteMenu(Long id);

}
