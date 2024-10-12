package cn.sichu.system.service;

import java.util.List;

/**
 * 角色和菜单业务接口
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public interface IRoleMenuService {

    /**
     * 新增
     *
     * @param menuIds 菜单ID 列表
     * @param roleId  角色ID
     * @return boolean 是否新增成功（true：成功；false：无变更/失败）
     * @author sichu huang
     * @date 2024/10/11
     **/
    boolean add(List<Long> menuIds, Long roleId);

    /**
     * 根据角色ID 删除
     *
     * @param roleIds 角色ID 列表
     * @author sichu huang
     * @date 2024/10/11
     **/
    void deleteByRoleIds(List<Long> roleIds);

    /**
     * 根据角色ID 查询
     *
     * @param roleIds 角色ID 列表
     * @return java.util.List<java.lang.Long> 菜单ID 列表
     * @author sichu huang
     * @date 2024/10/11
     **/
    List<Long> listMenuIdByRoleIds(List<Long> roleIds);
}
