package cn.sichu.utils.helper;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.JakartaServletUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.sichu.constant.CacheConstants;
import cn.sichu.core.utils.DateUtils;
import cn.sichu.core.utils.ExceptionUtils;
import cn.sichu.core.utils.IpUtils;
import cn.sichu.crud.core.service.CommonUserService;
import cn.sichu.model.dto.LoginUser;
import cn.sichu.web.utils.ServletUtils;
import cn.sichu.web.utils.SpringWebUtils;
import jakarta.servlet.http.HttpServletRequest;

import java.text.ParseException;
import java.util.Date;

/**
 * 登录助手
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public class LoginHelper {

    private LoginHelper() {
    }

    /**
     * 用户登录并缓存用户信息
     *
     * @param loginUser 登录用户信息
     * @return java.lang.String 令牌
     * @author sichu huang
     * @date 2024/10/12
     **/
    public static String login(LoginUser loginUser) throws ParseException {
        // 记录登录信息
        HttpServletRequest request = SpringWebUtils.getRequest();
        loginUser.setIp(JakartaServletUtil.getClientIP(request));
        loginUser.setAddress(
            ExceptionUtils.exToNull(() -> IpUtils.getIpv4Address(loginUser.getIp())));
        loginUser.setBrowser(ServletUtils.getBrowser(request));
        loginUser.setLoginTime(DateUtils.parseMillisecond(new Date()));
        loginUser.setOs(StrUtil.subBefore(ServletUtils.getOs(request), " or", false));
        // 登录并缓存用户信息
        StpUtil.login(loginUser.getId());
        SaHolder.getStorage().set(CacheConstants.LOGIN_USER_KEY, loginUser);
        String tokenValue = StpUtil.getTokenValue();
        loginUser.setToken(tokenValue);
        StpUtil.getTokenSession().set(CacheConstants.LOGIN_USER_KEY, loginUser);
        return tokenValue;
    }

    /**
     * 更新登录用户信息
     *
     * @param loginUser 登录用户信息
     * @param token     令牌
     * @author sichu huang
     * @date 2024/10/12
     **/
    public static void updateLoginUser(LoginUser loginUser, String token) {
        SaHolder.getStorage().delete(CacheConstants.LOGIN_USER_KEY);
        StpUtil.getTokenSessionByToken(token).set(CacheConstants.LOGIN_USER_KEY, loginUser);
    }

    /**
     * 获取登录用户信息
     *
     * @return cn.sichu.model.dto.LoginUser 登录用户信息
     * @author sichu huang
     * @date 2024/10/12
     **/
    public static LoginUser getLoginUser() throws NotLoginException {
        StpUtil.checkLogin();
        LoginUser loginUser = (LoginUser)SaHolder.getStorage().get(CacheConstants.LOGIN_USER_KEY);
        if (null != loginUser) {
            return loginUser;
        }
        SaSession tokenSession = StpUtil.getTokenSession();
        loginUser = (LoginUser)tokenSession.get(CacheConstants.LOGIN_USER_KEY);
        SaHolder.getStorage().set(CacheConstants.LOGIN_USER_KEY, loginUser);
        return loginUser;
    }

    /**
     * 根据 Token 获取登录用户信息
     *
     * @param token 用户 Token
     * @return cn.sichu.model.dto.LoginUser 登录用户信息
     * @author sichu huang
     * @date 2024/10/12
     **/
    public static LoginUser getLoginUser(String token) {
        SaSession tokenSession = StpUtil.getStpLogic().getTokenSessionByToken(token, false);
        if (null == tokenSession) {
            return null;
        }
        return (LoginUser)tokenSession.get(CacheConstants.LOGIN_USER_KEY);
    }

    /**
     * 获取登录用户 ID
     *
     * @return java.lang.Long 登录用户 ID
     * @author sichu huang
     * @date 2024/10/12
     **/
    public static Long getUserId() {
        return getLoginUser().getId();
    }

    /**
     * 获取登录用户名
     *
     * @return java.lang.String 登录用户名
     * @author sichu huang
     * @date 2024/10/12
     **/
    public static String getUsername() {
        return getLoginUser().getUsername();
    }

    /**
     * 获取登录用户昵称
     *
     * @return java.lang.String 登录用户昵称
     * @author sichu huang
     * @date 2024/10/12
     **/
    public static String getNickname() {
        return getNickname(getUserId());
    }

    /**
     * 获取登录用户昵称
     *
     * @param userId 登录用户 ID
     * @return java.lang.String 登录用户昵称
     * @author sichu huang
     * @date 2024/10/12
     **/
    public static String getNickname(Long userId) {
        return ExceptionUtils.exToNull(
            () -> SpringUtil.getBean(CommonUserService.class).getNicknameById(userId));
    }
}
