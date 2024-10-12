package cn.sichu.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.sichu.system.mapper.RoleMenuMapper;
import cn.sichu.system.model.entity.RoleMenuDo;
import cn.sichu.system.service.IRoleMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author sichu huang
 * @date 2024/10/11
 **/
@Service
@RequiredArgsConstructor
public class RoleMenuServiceImpl implements IRoleMenuService {
    
    private final RoleMenuMapper roleMenuMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean add(List<Long> menuIds, Long roleId) {
        // 检查是否有变更
        List<Long> oldMenuIdList =
            roleMenuMapper.lambdaQuery().select(RoleMenuDo::getMenuId)
                .eq(RoleMenuDo::getRoleId, roleId).list().stream()
                .map(RoleMenuDo::getMenuId).collect(Collectors.toList());
        if (CollUtil.isEmpty(CollUtil.disjunction(menuIds, oldMenuIdList))) {
            return false;
        }
        // 删除原有关联
        roleMenuMapper.lambdaUpdate().eq(RoleMenuDo::getRoleId, roleId)
            .remove();
        // 保存最新关联
        List<RoleMenuDo> roleMenuList =
            menuIds.stream().map(menuId -> new RoleMenuDo(roleId, menuId))
                .toList();
        return roleMenuMapper.insertBatch(roleMenuList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByRoleIds(List<Long> roleIds) {
        roleMenuMapper.lambdaUpdate().in(RoleMenuDo::getRoleId, roleIds)
            .remove();
    }

    @Override
    public List<Long> listMenuIdByRoleIds(List<Long> roleIds) {
        if (CollUtil.isEmpty(roleIds)) {
            return new ArrayList<>(0);
        }
        return roleMenuMapper.selectMenuIdByRoleIds(roleIds);
    }
}
