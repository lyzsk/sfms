package cn.sichu.system.service.impl;

import cn.crane4j.annotation.ContainerMethod;
import cn.crane4j.annotation.MappingType;
import cn.hutool.core.collection.CollUtil;
import cn.sichu.constant.ContainerConstants;
import cn.sichu.system.mapper.UserRoleMapper;
import cn.sichu.system.model.entity.UserRoleDo;
import cn.sichu.system.service.IUserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author sichu huang
 * @date 2024/10/11
 **/
@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl implements IUserRoleService {

    private final UserRoleMapper userRoleMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean add(List<Long> roleIds, Long userId) {
        // 检查是否有变更
        List<Long> oldRoleIdList = userRoleMapper.lambdaQuery().select(UserRoleDo::getRoleId)
            .eq(UserRoleDo::getUserId, userId).list().stream().map(UserRoleDo::getRoleId).toList();
        if (CollUtil.isEmpty(CollUtil.disjunction(roleIds, oldRoleIdList))) {
            return false;
        }
        // 删除原有关联
        userRoleMapper.lambdaUpdate().eq(UserRoleDo::getUserId, userId).remove();
        // 保存最新关联
        List<UserRoleDo> userRoleList =
            roleIds.stream().map(roleId -> new UserRoleDo(userId, roleId)).toList();
        return userRoleMapper.insertBatch(userRoleList);
    }

    @Override
    public void deleteByUserIds(List<Long> userIds) {
        userRoleMapper.lambdaUpdate().in(UserRoleDo::getUserId, userIds).remove();
    }

    @Override
    public void saveBatch(List<UserRoleDo> list) {
        userRoleMapper.insertBatch(list);
    }

    @Override
    @ContainerMethod(namespace = ContainerConstants.USER_ROLE_ID_LIST,
        type = MappingType.ORDER_OF_KEYS)
    public List<Long> listRoleIdByUserId(Long userId) {
        return userRoleMapper.selectRoleIdByUserId(userId);
    }

    @Override
    public boolean isRoleIdExists(List<Long> roleIds) {
        return userRoleMapper.lambdaQuery().in(UserRoleDo::getRoleId, roleIds).exists();
    }

}
