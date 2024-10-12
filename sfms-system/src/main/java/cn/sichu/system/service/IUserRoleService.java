package cn.sichu.system.service;

import cn.sichu.system.model.entity.UserRoleDo;

import java.util.List;

/**
 * 用户和角色业务接口
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public interface IUserRoleService {
    /**
     * 新增
     *
     * @param roleIds 角色ID列表
     * @param userId  用户ID
     * @return boolean 是否新增成功（true：成功；false：无变更/失败）
     * @author sichu huang
     * @date 2024/10/11
     **/
    boolean add(List<Long> roleIds, Long userId);

    /**
     * 根据用户ID删除
     *
     * @author sichu huang
     * @date 2024/10/11
     **/
    void deleteByUserIds(List<Long> userIds);

    /**
     * 批量插入
     *
     * @param list 数据集
     * @author sichu huang
     * @date 2024/10/11
     **/
    void saveBatch(List<UserRoleDo> list);

    /**
     * 根据用户ID 查询
     *
     * @param userId 用户ID
     * @return java.util.List<java.lang.Long> 角色ID列表
     * @author sichu huang
     * @date 2024/10/11
     **/
    List<Long> listRoleIdByUserId(Long userId);

    /**
     * 根据角色ID 判断是否已被用户关联
     *
     * @param roleIds 角色ID列表
     * @return boolean 是否已关联（true：已关联；false：未关联）
     * @author sichu huang
     * @date 2024/10/11
     **/
    boolean isRoleIdExists(List<Long> roleIds);
}
