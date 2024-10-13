package cn.sichu.controller.common;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.lang.RegexPool;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.RandomUtil;
import cn.sichu.cache.redisson.utils.RedisUtils;
import cn.sichu.config.properties.CaptchaProperties;
import cn.sichu.constant.CacheConstants;
import cn.sichu.core.autoconfigure.project.ProjectProperties;
import cn.sichu.core.utils.TemplateUtils;
import cn.sichu.core.utils.validate.CheckUtils;
import cn.sichu.messaging.mail.utils.MailUtils;
import cn.sichu.security.limiter.annotation.RateLimiter;
import cn.sichu.security.limiter.annotation.RateLimiters;
import cn.sichu.security.limiter.enums.LimitType;
import cn.sichu.system.service.IOptionService;
import cn.sichu.web.model.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.dromara.sms4j.api.SmsBlend;
import org.dromara.sms4j.api.entity.SmsResponse;
import org.dromara.sms4j.comm.constant.SupplierConstant;
import org.dromara.sms4j.core.factory.SmsFactory;
import org.redisson.api.RateIntervalUnit;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 验证码 API
 *
 * @author sichu huang
 * @date 2024/10/13
 **/
@Tag(name = "验证码 API")
@SaIgnore
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/captcha")
public class CaptchaController {
    private final ProjectProperties projectProperties;
    private final CaptchaProperties captchaProperties;
    private final IOptionService optionService;

    /**
     * 获取邮箱验证码
     *
     * <p>
     * 限流规则：<br>
     * 1.同一邮箱同一模板，1分钟2条，1小时8条，24小时20条 <br>
     * 2、同一邮箱所有模板 24 小时 100 条 <br>
     * 3、同一 IP 每分钟限制发送 30 条
     * </p>
     *
     * @param email email
     * @return cn.sichu.web.model.R
     * @author sichu huang
     * @date 2024/10/13
     **/
    @Operation(summary = "获取邮箱验证码", description = "发送验证码到指定邮箱")
    @GetMapping("/mail")
    @RateLimiters({@RateLimiter(name = CacheConstants.CAPTCHA_KEY_PREFIX + "MIN",
        key = "#email + ':' + T(cn.hutool.extra.spring.SpringUtil).getProperty('captcha.mail.templatePath')",
        rate = 2, interval = 1, unit = RateIntervalUnit.MINUTES,
        message = "获取验证码操作太频繁，请稍后再试"),
        @RateLimiter(name = CacheConstants.CAPTCHA_KEY_PREFIX + "HOUR",
            key = "#email + ':' + T(cn.hutool.extra.spring.SpringUtil).getProperty('captcha.mail.templatePath')",
            rate = 8, interval = 1, unit = RateIntervalUnit.HOURS,
            message = "获取验证码操作太频繁，请稍后再试"),
        @RateLimiter(name = CacheConstants.CAPTCHA_KEY_PREFIX + "DAY'",
            key = "#email + ':' + T(cn.hutool.extra.spring.SpringUtil).getProperty('captcha.mail.templatePath')",
            rate = 20, interval = 24, unit = RateIntervalUnit.HOURS,
            message = "获取验证码操作太频繁，请稍后再试"),
        @RateLimiter(name = CacheConstants.CAPTCHA_KEY_PREFIX, key = "#email", rate = 100,
            interval = 24, unit = RateIntervalUnit.HOURS,
            message = "获取验证码操作太频繁，请稍后再试"),
        @RateLimiter(name = CacheConstants.CAPTCHA_KEY_PREFIX, key = "#email", rate = 30,
            interval = 1, unit = RateIntervalUnit.MINUTES, type = LimitType.IP,
            message = "获取验证码操作太频繁，请稍后再试")})
    public R getMailCaptcha(@NotBlank(message = "邮箱不能为空") @Pattern(regexp = RegexPool.EMAIL,
        message = "邮箱格式错误") String email) throws MessagingException {
        // 生成验证码
        CaptchaProperties.CaptchaMail captchaMail = captchaProperties.getMail();
        String captcha = RandomUtil.randomNumbers(captchaMail.getLength());
        // 发送验证码
        Long expirationInMinutes = captchaMail.getExpirationInMinutes();
        Map<String, String> siteConfig = optionService.getByCategory("SITE");
        String content = TemplateUtils.render(captchaMail.getTemplatePath(),
            Dict.create().set("siteUrl", projectProperties.getUrl())
                .set("siteTitle", siteConfig.get("SITE_TITLE"))
                .set("siteCopyright", siteConfig.get("SITE_COPYRIGHT")).set("captcha", captcha)
                .set("expiration", expirationInMinutes));
        MailUtils.sendHtml(email, "【%s】邮箱验证码".formatted(projectProperties.getName()), content);
        // 保存验证码
        String captchaKey = CacheConstants.CAPTCHA_KEY_PREFIX + email;
        RedisUtils.set(captchaKey, captcha, Duration.ofMinutes(expirationInMinutes));
        return R.ok("发送成功，验证码有效期 %s 分钟".formatted(expirationInMinutes));
    }

    /**
     * 获取短信验证码
     *
     * <p>
     * 限流规则：<br>
     * 1.同一号码同一模板，1分钟2条，1小时8条，24小时20条 <br>
     * 2、同一号码所有模板 24 小时 100 条 <br>
     * 3、同一 IP 每分钟限制发送 30 条
     * </p>
     *
     * @param phone phone
     * @return cn.sichu.web.model.R
     * @author sichu huang
     * @date 2024/10/13
     **/
    @Operation(summary = "获取短信验证码", description = "发送验证码到指定手机号")
    @GetMapping("/sms")
    @RateLimiters({@RateLimiter(name = CacheConstants.CAPTCHA_KEY_PREFIX + "MIN",
        key = "#phone + ':' + T(cn.hutool.extra.spring.SpringUtil).getProperty('captcha.sms.templateId')",
        rate = 2, interval = 1, unit = RateIntervalUnit.MINUTES,
        message = "获取验证码操作太频繁，请稍后再试"),
        @RateLimiter(name = CacheConstants.CAPTCHA_KEY_PREFIX + "HOUR",
            key = "#phone + ':' + T(cn.hutool.extra.spring.SpringUtil).getProperty('captcha.sms.templateId')",
            rate = 8, interval = 1, unit = RateIntervalUnit.HOURS,
            message = "获取验证码操作太频繁，请稍后再试"),
        @RateLimiter(name = CacheConstants.CAPTCHA_KEY_PREFIX + "DAY'",
            key = "#phone + ':' + T(cn.hutool.extra.spring.SpringUtil).getProperty('captcha.sms.templateId')",
            rate = 20, interval = 24, unit = RateIntervalUnit.HOURS,
            message = "获取验证码操作太频繁，请稍后再试"),
        @RateLimiter(name = CacheConstants.CAPTCHA_KEY_PREFIX, key = "#phone", rate = 100,
            interval = 24, unit = RateIntervalUnit.HOURS,
            message = "获取验证码操作太频繁，请稍后再试"),
        @RateLimiter(name = CacheConstants.CAPTCHA_KEY_PREFIX, key = "#phone", rate = 30,
            interval = 1, unit = RateIntervalUnit.MINUTES, type = LimitType.IP,
            message = "获取验证码操作太频繁，请稍后再试")})
    public R getSmsCaptcha(@NotBlank(message = "手机号不能为空") @Pattern(regexp = RegexPool.MOBILE,
        message = "手机号格式错误") String phone) {
        CaptchaProperties.CaptchaSms captchaSms = captchaProperties.getSms();
        // 生成验证码
        String captcha = RandomUtil.randomNumbers(captchaSms.getLength());
        // 发送验证码
        Long expirationInMinutes = captchaSms.getExpirationInMinutes();
        SmsBlend smsBlend = SmsFactory.getBySupplier(SupplierConstant.CLOOPEN);
        Map<String, String> messageMap = MapUtil.newHashMap(2, true);
        messageMap.put("captcha", captcha);
        messageMap.put("expirationInMinutes", String.valueOf(expirationInMinutes));
        SmsResponse smsResponse = smsBlend.sendMessage(phone, captchaSms.getTemplateId(),
            (LinkedHashMap<String, String>)messageMap);
        CheckUtils.throwIf(!smsResponse.isSuccess(), "验证码发送失败");
        // 保存验证码
        String captchaKey = CacheConstants.CAPTCHA_KEY_PREFIX + phone;
        RedisUtils.set(captchaKey, captcha, Duration.ofMinutes(expirationInMinutes));
        return R.ok("发送成功，验证码有效期 %s 分钟".formatted(expirationInMinutes));
    }
}
