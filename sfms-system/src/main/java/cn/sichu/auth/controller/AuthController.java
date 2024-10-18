package cn.sichu.auth.controller;

import cn.sichu.annotation.Log;
import cn.sichu.auth.service.AuthService;
import cn.sichu.enums.LogModuleEnum;
import cn.sichu.result.Result;
import cn.sichu.system.model.dto.CaptchaResult;
import cn.sichu.system.model.dto.LoginResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author sichu huang
 * @since 2024/10/17 17:18
 */
@Slf4j
@Tag(name = "01.认证中心")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "登录")
    @PostMapping("/login")
    @Log(value = "登录", module = LogModuleEnum.LOGIN)
    public Result<LoginResult> login(
        @Parameter(description = "用户名", example = "admin") @RequestParam String username,
        @Parameter(description = "密码", example = "123456") @RequestParam String password) {
        LoginResult loginResult = authService.login(username, password);
        return Result.success(loginResult);
    }

    @Operation(summary = "注销")
    @DeleteMapping("/logout")
    @Log(value = "注销", module = LogModuleEnum.LOGIN)
    public Result<?> logout() {
        authService.logout();
        return Result.success();
    }

    @Operation(summary = "获取验证码")
    @GetMapping("/captcha")
    public Result<CaptchaResult> getCaptcha() {
        CaptchaResult captcha = authService.getCaptcha();
        return Result.success(captcha);
    }
}
