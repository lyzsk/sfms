package cn.sichu.system.service;

import java.util.List;

/**
 * 角色和部门业务接口
 *
 * @author sichu huang
 * @date 2024/10/10
 **/
public interface IRoleDeptService {
    /**
     * 新增
     *
     * @param deptIds 部门ID列表
     * @param roleId  角色ID
     * @return boolean
     * @author sichu huang
     * @date 2024/10/10
     **/
    boolean add(List<Long> deptIds, Long roleId);

    /**
     * 根据角色ID删除
     *
     * @param
     * @param roleIds 角色ID列表
     * @author sichu huang
     * @date 2024/10/10
     **/
    void deleteByRoleIds(List<Long> roleIds);

    /**
     * 根据部门ID删除
     *
     * @param deptIds 部门ID列表
     * @author sichu huang
     * @date 2024/10/10
     **/
    void deleteByDeptIds(List<Long> deptIds);

    /**
     * 根据角色ID查询
     *
     * @param roleId 角色ID
     * @return java.util.List<java.lang.Long> 部门ID列表
     * @author sichu huang
     * @date 2024/10/10
     **/
    List<Long> listDeptIdByRoleId(Long roleId);
}
