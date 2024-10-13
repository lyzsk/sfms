package cn.sichu.config.satoken;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpInterface;
import cn.sichu.auth.satoken.autoconfigure.SaTokenExtensionProperties;
import cn.sichu.core.constant.StringConstants;
import cn.sichu.core.utils.validate.CheckUtils;
import cn.sichu.model.dto.LoginUser;
import cn.sichu.utils.helper.LoginHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Sa-Token 配置
 *
 * @author sichu huang
 * @date 2024/10/13
 **/
@Configuration
@RequiredArgsConstructor
public class SaTokenConfiguration {

    private final SaTokenExtensionProperties properties;
    private final LoginPasswordProperties loginPasswordProperties;

    /**
     * Sa-Token 权限认证配置
     **/
    @Bean
    public StpInterface stpInterface() {
        return new SaTokenPermissionImpl();
    }

    /**
     * SaToken 拦截器配置
     **/
    @Bean
    public SaInterceptor saInterceptor() {
        return new SaInterceptor(handle -> SaRouter.match(StringConstants.PATH_PATTERN)
            .notMatch(properties.getSecurity().getExcludes()).check(r -> {
                LoginUser loginUser = LoginHelper.getLoginUser();
                if (SaRouter.isMatchCurrURI(loginPasswordProperties.getExcludes())) {
                    return;
                }
                CheckUtils.throwIf(loginUser.isPasswordExpired(), "密码已过期，请修改密码");
            }));
    }
}
