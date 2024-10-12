package cn.sichu.auth.service;

import cn.sichu.auth.model.resp.RouteResp;
import jakarta.servlet.http.HttpServletRequest;

import java.text.ParseException;
import java.util.List;

/**
 * 登录业务接口
 *
 * @author sichu huang
 * @date 2024/10/10
 **/
public interface ILoginService {

    /**
     * 账号登录
     *
     * @param username 用户名
     * @param password 密码
     * @param request  请求对象
     * @return java.lang.String 令牌
     * @author sichu huang
     * @date 2024/10/10
     **/
    String accountLogin(String username, String password, HttpServletRequest request)
        throws ParseException;

    /**
     * 手机号登录
     *
     * @param phone 手机号
     * @return java.lang.String 令牌
     * @author sichu huang
     * @date 2024/10/10
     **/
    String phoneLogin(String phone) throws ParseException;

    /**
     * 邮箱登录
     *
     * @param email 邮箱
     * @return java.lang.String 令牌
     * @author sichu huang
     * @date 2024/10/10
     **/
    String emailLogin(String email) throws ParseException;

    /**
     * 构建路由树
     *
     * @param userId 用户 ID
     * @return java.util.List<RouteResp>
     * @author sichu huang
     * @date 2024/10/10
     **/
    List<RouteResp> buildRouteTree(Long userId);
}
