package cn.sichu.system.service;

import cn.sichu.crud.mp.service.BaseService;
import cn.sichu.data.mp.service.IService;
import cn.sichu.model.dto.RoleDTO;
import cn.sichu.system.model.entity.RoleDO;
import cn.sichu.system.model.query.RoleQuery;
import cn.sichu.system.model.req.RoleReq;
import cn.sichu.system.model.resp.RoleDetailResp;
import cn.sichu.system.model.resp.RoleResp;

import java.util.List;
import java.util.Set;

/**
 * 角色业务接口
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public interface IRoleService
    extends BaseService<RoleResp, RoleDetailResp, RoleQuery, RoleReq>, IService<RoleDO> {

    /**
     * 根据用户ID 查询权限码
     *
     * @param userId 用户ID
     * @return java.util.Set<java.lang.String> 权限码集合
     * @author sichu huang
     * @date 2024/10/11
     **/
    Set<String> listPermissionByUserId(Long userId);

    /**
     * 根据 ID 列表查询
     *
     * @param ids ID 列表
     * @return java.util.List<java.lang.String> 名称列表
     * @author sichu huang
     * @date 2024/10/11
     **/
    List<String> listNameByIds(List<Long> ids);

    /**
     * 根据用户ID 查询角色编码
     *
     * @param userId 用户ID
     * @return java.util.Set<java.lang.String> 角色编码集合
     * @author sichu huang
     * @date 2024/10/11
     **/
    Set<String> listCodeByUserId(Long userId);

    /**
     * 根据用户ID 查询角色
     *
     * @param userId 用户ID
     * @return java.util.Set<cn.sichu.model.dto.RoleDto> 角色集合
     * @author sichu huang
     * @date 2024/10/11
     **/
    Set<RoleDTO> listByUserId(Long userId);

    /**
     * 根据角色编码查询
     *
     * @param code 角色编码
     * @return cn.sichu.system.model.entity.RoleDo 角色信息
     * @author sichu huang
     * @date 2024/10/11
     **/
    RoleDO getByCode(String code);

    /**
     * 根据角色名称查询
     *
     * @param list 名称列表
     * @return java.util.List<cn.sichu.system.model.entity.RoleDo> 角色列表
     * @author sichu huang
     * @date 2024/10/11
     **/
    List<RoleDO> listByNames(List<String> list);

    /**
     * 根据角色名称查询数量
     *
     * @param roleNames 名称列表
     * @return int 角色数量
     * @author sichu huang
     * @date 2024/10/11
     **/
    int countByNames(List<String> roleNames);
}
