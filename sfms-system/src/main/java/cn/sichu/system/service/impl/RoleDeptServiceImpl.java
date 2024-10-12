package cn.sichu.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.sichu.system.mapper.RoleDeptMapper;
import cn.sichu.system.model.entity.RoleDeptDo;
import cn.sichu.system.service.IRoleDeptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色和部门业务实现
 *
 * @author sichu huang
 * @date 2024/10/10
 **/
@Service
@RequiredArgsConstructor
public class RoleDeptServiceImpl implements IRoleDeptService {
    private final RoleDeptMapper roleDeptMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean add(List<Long> deptIds, Long roleId) {
        // 检查是否有变更
        List<Long> oldDeptIdList =
            roleDeptMapper.lambdaQuery().select(RoleDeptDo::getDeptId)
                .eq(RoleDeptDo::getRoleId, roleId).list().stream()
                .map(RoleDeptDo::getDeptId).toList();
        if (CollUtil.isEmpty(CollUtil.disjunction(deptIds, oldDeptIdList))) {
            return false;
        }
        // 删除原有关联
        roleDeptMapper.lambdaUpdate().eq(RoleDeptDo::getRoleId, roleId)
            .remove();
        // 保存最新关联
        List<RoleDeptDo> roleDeptList =
            deptIds.stream().map(deptId -> new RoleDeptDo(roleId, deptId))
                .toList();
        return roleDeptMapper.insertBatch(roleDeptList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByRoleIds(List<Long> roleIds) {
        roleDeptMapper.lambdaUpdate().in(RoleDeptDo::getRoleId, roleIds)
            .remove();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByDeptIds(List<Long> deptIds) {
        roleDeptMapper.lambdaUpdate().in(RoleDeptDo::getDeptId, deptIds)
            .remove();
    }

    @Override
    public List<Long> listDeptIdByRoleId(Long roleId) {
        return roleDeptMapper.selectDeptIdByRoleId(roleId);
    }
}
