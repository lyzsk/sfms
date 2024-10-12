package cn.sichu.system.service;

import cn.sichu.crud.mp.service.BaseService;
import cn.sichu.data.mp.service.IService;
import cn.sichu.system.model.entity.MenuDO;
import cn.sichu.system.model.query.MenuQuery;
import cn.sichu.system.model.req.MenuReq;
import cn.sichu.system.model.resp.MenuResp;

import java.util.List;
import java.util.Set;

/**
 * 菜单业务接口
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public interface IMenuService
    extends BaseService<MenuResp, MenuResp, MenuQuery, MenuReq>, IService<MenuDO> {
    /**
     * 查询全部菜单
     *
     * @return java.util.List<cn.sichu.system.model.resp.MenuResp> 菜单列表
     * @author sichu huang
     * @date 2024/10/11
     **/
    List<MenuResp> listAll();

    /**
     * 根据用户ID 查询
     *
     * @param userId 用户ID
     * @return java.util.Set<java.lang.String> 权限码集合
     * @author sichu huang
     * @date 2024/10/11
     **/
    Set<String> listPermissionByUserId(Long userId);

    /**
     * 根据角色编码查询
     *
     * @param roleCode 角色编码
     * @return java.util.List<cn.sichu.system.model.resp.MenuResp> 菜单列表
     * @author sichu huang
     * @date 2024/10/11
     **/
    List<MenuResp> listByRoleCode(String roleCode);
}
