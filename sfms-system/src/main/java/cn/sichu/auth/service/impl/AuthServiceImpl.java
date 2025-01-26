package cn.sichu.auth.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import cn.sichu.auth.service.AuthService;
import cn.sichu.constant.SecurityConstants;
import cn.sichu.system.core.utils.JwtUtils;
import cn.sichu.system.model.dto.LoginResult;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.concurrent.TimeUnit;

/**
 * @author sichu huang
 * @since 2024/10/17 16:16
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public LoginResult login(String username, String password) {
        // 创建认证令牌对象
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(username.toLowerCase().trim(), password);
        // 执行用户认证
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        // 认证成功后生成JWT令牌
        String accessToken = JwtUtils.createToken(authentication);
        // 将认证信息存入Security上下文，便于在AOP（如日志记录）中获取当前用户信息
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 返回包含JWT令牌的登录结果
        return LoginResult.builder().tokenType("Bearer").accessToken(accessToken).build();
    }

    @Override
    public void logout() {
        HttpServletRequest request =
            ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StrUtil.isNotBlank(token) && token.startsWith(SecurityConstants.JWT_TOKEN_PREFIX)) {
            token = token.substring(SecurityConstants.JWT_TOKEN_PREFIX.length());
            // 解析Token以获取有效载荷（payload）
            JSONObject payloads = JWTUtil.parseToken(token).getPayloads();
            // 解析 Token 获取 jti(JWT ID) 和 exp(过期时间)
            String jti = payloads.getStr(JWTPayload.JWT_ID);
            Long expiration = payloads.getLong(JWTPayload.EXPIRES_AT); // 过期时间(秒)
            // 如果exp存在，则计算Token剩余有效时间
            if (expiration != null) {
                long currentTimeSeconds = System.currentTimeMillis() / 1000;
                if (expiration < currentTimeSeconds) {
                    // Token已过期，不再加入黑名单
                    return;
                }
                // 将Token的jti加入黑名单，并设置剩余有效时间，使其在过期后自动从黑名单移除
                long ttl = expiration - currentTimeSeconds;
                redisTemplate.opsForValue()
                    .set(SecurityConstants.BLACKLIST_TOKEN_PREFIX + jti, null, ttl,
                        TimeUnit.SECONDS);
            } else {
                // 如果exp不存在，说明Token永不过期，则永久加入黑名单
                redisTemplate.opsForValue()
                    .set(SecurityConstants.BLACKLIST_TOKEN_PREFIX + jti, null);
            }
        }
        // 清空Spring Security上下文
        SecurityContextHolder.clearContext();
    }

}
