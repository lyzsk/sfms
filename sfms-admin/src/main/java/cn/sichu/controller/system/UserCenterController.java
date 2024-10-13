package cn.sichu.controller.system;

import cn.sichu.cache.redisson.utils.RedisUtils;
import cn.sichu.constant.CacheConstants;
import cn.sichu.core.utils.ExceptionUtils;
import cn.sichu.core.utils.validate.ValidationUtils;
import cn.sichu.system.model.req.UserBasicInfoUpdateReq;
import cn.sichu.system.model.req.UserEmailUpdateRequest;
import cn.sichu.system.model.req.UserPasswordUpdateReq;
import cn.sichu.system.model.req.UserPhoneUpdateReq;
import cn.sichu.system.model.resp.AvatarResp;
import cn.sichu.system.service.IUserService;
import cn.sichu.utils.SecureUtils;
import cn.sichu.utils.helper.LoginHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 个人中心 API
 *
 * @author sichu huang
 * @date 2024/10/13
 **/
@Tag(name = "个人中心 API")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/user")
public class UserCenterController {

    private static final String DECRYPT_FAILED = "当前密码解密失败";
    private static final String CAPTCHA_EXPIRED = "验证码已失效";
    private final IUserService userService;

    @Operation(summary = "修改头像", description = "用户修改个人头像")
    @PostMapping("/avatar")
    public AvatarResp updateAvatar(@NotNull(message = "头像不能为空") MultipartFile avatarFile)
        throws IOException {
        ValidationUtils.throwIf(avatarFile::isEmpty, "头像不能为空");
        String newAvatar = userService.updateAvatar(avatarFile, LoginHelper.getUserId());
        return AvatarResp.builder().avatar(newAvatar).build();
    }

    @Operation(summary = "修改基础信息", description = "修改用户基础信息")
    @PatchMapping("/basic/info")
    public void updateBasicInfo(@Validated @RequestBody UserBasicInfoUpdateReq req) {
        userService.updateBasicInfo(req, LoginHelper.getUserId());
    }

    @Operation(summary = "修改密码", description = "修改用户登录密码")
    @PatchMapping("/password")
    public void updatePassword(@Validated @RequestBody UserPasswordUpdateReq updateReq) {
        String rawOldPassword = ExceptionUtils.exToNull(
            () -> SecureUtils.decryptByRsaPrivateKey(updateReq.getOldPassword()));
        ValidationUtils.throwIfNull(rawOldPassword, DECRYPT_FAILED);
        String rawNewPassword = ExceptionUtils.exToNull(
            () -> SecureUtils.decryptByRsaPrivateKey(updateReq.getNewPassword()));
        ValidationUtils.throwIfNull(rawNewPassword, "新密码解密失败");
        userService.updatePassword(rawOldPassword, rawNewPassword, LoginHelper.getUserId());
    }

    @Operation(summary = "修改手机号", description = "修改手机号")
    @PatchMapping("/phone")
    public void updatePhone(@Validated @RequestBody UserPhoneUpdateReq updateReq) {
        String rawOldPassword = ExceptionUtils.exToNull(
            () -> SecureUtils.decryptByRsaPrivateKey(updateReq.getOldPassword()));
        ValidationUtils.throwIfBlank(rawOldPassword, DECRYPT_FAILED);
        String captchaKey = CacheConstants.CAPTCHA_KEY_PREFIX + updateReq.getPhone();
        String captcha = RedisUtils.get(captchaKey);
        ValidationUtils.throwIfBlank(captcha, CAPTCHA_EXPIRED);
        ValidationUtils.throwIfNotEqualIgnoreCase(updateReq.getCaptcha(), captcha, "验证码错误");
        RedisUtils.delete(captchaKey);
        userService.updatePhone(updateReq.getPhone(), rawOldPassword, LoginHelper.getUserId());
    }

    @Operation(summary = "修改邮箱", description = "修改用户邮箱")
    @PatchMapping("/email")
    public void updateEmail(@Validated @RequestBody UserEmailUpdateRequest updateReq) {
        String rawOldPassword = ExceptionUtils.exToNull(
            () -> SecureUtils.decryptByRsaPrivateKey(updateReq.getOldPassword()));
        ValidationUtils.throwIfBlank(rawOldPassword, DECRYPT_FAILED);
        String captchaKey = CacheConstants.CAPTCHA_KEY_PREFIX + updateReq.getEmail();
        String captcha = RedisUtils.get(captchaKey);
        ValidationUtils.throwIfBlank(captcha, CAPTCHA_EXPIRED);
        ValidationUtils.throwIfNotEqualIgnoreCase(updateReq.getCaptcha(), captcha, "验证码错误");
        RedisUtils.delete(captchaKey);
        userService.updateEmail(updateReq.getEmail(), rawOldPassword, LoginHelper.getUserId());
    }
}
