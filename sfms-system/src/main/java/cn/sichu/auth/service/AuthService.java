package cn.sichu.auth.service;

import cn.sichu.system.model.dto.LoginResult;

/**
 * @author sichu huang
 * @since 2024/10/17 16:14
 */
public interface AuthService {

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @return cn.sichu.model.dto.LoginResult 登录结果
     * @author sichu huang
     * @since 2024/10/17 16:15:32
     */
    LoginResult login(String username, String password);

    /**
     * 登出
     *
     * @author sichu huang
     * @since 2024/10/17 16:15:43
     */
    void logout();

}
