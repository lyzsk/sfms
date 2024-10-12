package cn.sichu.system.service;

import java.util.List;

/**
 * 用户历史密码业务接口
 *
 * @author sichu huang
 * @date 2024/10/10
 **/
public interface IUserPasswordHistoryService {
    /**
     * 新增
     *
     * @param userId   用户ID
     * @param password 密码
     * @param count    保留N个历史
     * @author sichu huang
     * @date 2024/10/10
     **/
    void add(Long userId, String password, int count);

    /**
     * 根据用户ID删除
     *
     * @param userIds 用户ID列表
     * @author sichu huang
     * @date 2024/10/10
     **/
    void deleteByUserIds(List<Long> userIds);

    /**
     * 密码是否为重复使用
     *
     * @param userId   用户ID
     * @param password 密码
     * @param count    最近N次
     * @return boolean
     * @author sichu huang
     * @date 2024/10/10
     **/
    boolean isPasswordReused(Long userId, String password, int count);
}
