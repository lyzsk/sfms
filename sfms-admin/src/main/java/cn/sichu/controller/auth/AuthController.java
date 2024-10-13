package cn.sichu.controller.auth;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.sichu.auth.model.req.AccountLoginReq;
import cn.sichu.auth.model.req.EmailLoginReq;
import cn.sichu.auth.model.req.PhoneLoginReq;
import cn.sichu.auth.model.resp.LoginResp;
import cn.sichu.auth.model.resp.RouteResp;
import cn.sichu.auth.model.resp.UserInfoResp;
import cn.sichu.auth.service.ILoginService;
import cn.sichu.cache.redisson.utils.RedisUtils;
import cn.sichu.constant.CacheConstants;
import cn.sichu.core.utils.ExceptionUtils;
import cn.sichu.core.utils.validate.ValidationUtils;
import cn.sichu.log.annotation.Log;
import cn.sichu.model.dto.LoginUser;
import cn.sichu.system.model.resp.UserDetailResp;
import cn.sichu.system.service.IUserService;
import cn.sichu.utils.SecureUtils;
import cn.sichu.utils.helper.LoginHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

/**
 * 认证 API
 *
 * @author sichu huang
 * @date 2024/10/13
 **/
@Log(module = "登录")
@Tag(name = "认证 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private static final String CAPTCHA_EXPIRED = "验证码已失效";
    private static final String CAPTCHA_ERROR = "验证码错误";
    private final ILoginService loginService;
    private final IUserService userService;

    @SaIgnore
    @Operation(summary = "账号登录", description = "根据账号和密码进行登录认证")
    @PostMapping("/account")
    public LoginResp accountLogin(@Validated @RequestBody AccountLoginReq loginReq,
        HttpServletRequest request) throws ParseException {
        // 用户登录
        String rawPassword = ExceptionUtils.exToNull(
            () -> SecureUtils.decryptByRsaPrivateKey(loginReq.getPassword()));
        ValidationUtils.throwIfBlank(rawPassword, "密码解密失败");
        String token = loginService.accountLogin(loginReq.getUsername(), rawPassword, request);
        return LoginResp.builder().token(token).build();
    }

    @SaIgnore
    @Operation(summary = "手机号登录", description = "根据手机号和验证码进行登录认证")
    @PostMapping("/phone")
    public LoginResp phoneLogin(@Validated @RequestBody PhoneLoginReq loginReq)
        throws ParseException {
        String phone = loginReq.getPhone();
        String captchaKey = CacheConstants.CAPTCHA_KEY_PREFIX + phone;
        String captcha = RedisUtils.get(captchaKey);
        ValidationUtils.throwIfBlank(captcha, CAPTCHA_EXPIRED);
        ValidationUtils.throwIfNotEqualIgnoreCase(loginReq.getCaptcha(), captcha, CAPTCHA_ERROR);
        RedisUtils.delete(captchaKey);
        String token = loginService.phoneLogin(phone);
        return LoginResp.builder().token(token).build();
    }

    @SaIgnore
    @Operation(summary = "邮箱登录", description = "根据邮箱和验证码进行登录认证")
    @PostMapping("/email")
    public LoginResp emailLogin(@Validated @RequestBody EmailLoginReq loginReq)
        throws ParseException {
        String email = loginReq.getEmail();
        String captchaKey = CacheConstants.CAPTCHA_KEY_PREFIX + email;
        String captcha = RedisUtils.get(captchaKey);
        ValidationUtils.throwIfBlank(captcha, CAPTCHA_EXPIRED);
        ValidationUtils.throwIfNotEqualIgnoreCase(loginReq.getCaptcha(), captcha, CAPTCHA_ERROR);
        RedisUtils.delete(captchaKey);
        String token = loginService.emailLogin(email);
        return LoginResp.builder().token(token).build();
    }

    @Operation(summary = "用户退出", description = "注销用户的当前登录")
    @Parameter(name = "Authorization", description = "令牌", required = true,
        example = "Bearer xxxx-xxxx-xxxx-xxxx", in = ParameterIn.HEADER)
    @PostMapping("/logout")
    public Object logout() {
        Object loginId = StpUtil.getLoginId(-1L);
        StpUtil.logout();
        return loginId;
    }

    @Log(ignore = true)
    @Operation(summary = "获取用户信息", description = "获取登录用户信息")
    @GetMapping("/user/info")
    public UserInfoResp getUserInfo() {
        LoginUser loginUser = LoginHelper.getLoginUser();
        UserDetailResp userDetailResp = userService.get(loginUser.getId());
        UserInfoResp userInfoResp = BeanUtil.copyProperties(userDetailResp, UserInfoResp.class);
        userInfoResp.setPermissions(loginUser.getPermissions());
        userInfoResp.setRoles(loginUser.getRoleCodes());
        userInfoResp.setPwdExpired(loginUser.isPasswordExpired());
        return userInfoResp;
    }

    @Log(ignore = true)
    @Operation(summary = "获取路由信息", description = "获取登录用户的路由信息")
    @GetMapping("/route")
    public List<RouteResp> listRoute() {
        return loginService.buildRouteTree(LoginHelper.getUserId());
    }
}
