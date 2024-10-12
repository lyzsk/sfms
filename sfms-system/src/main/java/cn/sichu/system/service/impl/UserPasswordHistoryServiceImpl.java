package cn.sichu.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.sichu.system.mapper.UserPasswordHistoryMapper;
import cn.sichu.system.model.entity.UserPasswordHistoryDo;
import cn.sichu.system.service.IUserPasswordHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户历史密码业务实现
 *
 * @author sichu huang
 * @date 2024/10/10
 **/
@Service
@RequiredArgsConstructor
public class UserPasswordHistoryServiceImpl
    implements IUserPasswordHistoryService {
    private final UserPasswordHistoryMapper userPasswordHistoryMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(Long userId, String password, int count) {
        if (StrUtil.isBlank(password)) {
            return;
        }
        userPasswordHistoryMapper.insert(
            new UserPasswordHistoryDo(userId, password));
        // 删除过期历史密码
        userPasswordHistoryMapper.deleteExpired(userId, count);
    }

    @Override
    public void deleteByUserIds(List<Long> userIds) {
        userPasswordHistoryMapper.lambdaUpdate()
            .in(UserPasswordHistoryDo::getUserId, userIds).remove();
    }

    @Override
    public boolean isPasswordReused(Long userId, String password, int count) {
        // 查询近 N 个历史密码
        List<UserPasswordHistoryDo> list =
            userPasswordHistoryMapper.lambdaQuery()
                .select(UserPasswordHistoryDo::getPassword)
                .eq(UserPasswordHistoryDo::getUserId, userId)
                .orderByDesc(UserPasswordHistoryDo::getCreateTime)
                .last("LIMIT %s".formatted(count)).list();
        if (CollUtil.isEmpty(list)) {
            return false;
        }
        // 校验是否重复使用历史密码
        List<String> passwordList =
            list.stream().map(UserPasswordHistoryDo::getPassword).toList();
        return passwordList.stream()
            .anyMatch(p -> passwordEncoder.matches(password, p));
    }
}
