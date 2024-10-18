package cn.sichu.system.core.utils;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import cn.sichu.constant.JwtClaimConstants;
import cn.sichu.system.core.model.SysUserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * JWT Token 工具类
 *
 * @author sichu huang
 * @since 2024/10/16 21:53
 */
@Component
public class JwtUtils {
    /**
     * JWT 加解密使用的密钥
     */
    private static byte[] key;

    /**
     * JWT Token 的有效时间(单位:秒)
     */
    private static int ttl;

    /**
     * 生成 JWT Token
     *
     * @param authentication 用户认证信息
     * @return java.lang.String Token
     * @author sichu huang
     * @since 2024/10/16 23:45:00
     */
    public static String createToken(Authentication authentication) {
        SysUserDetails userDetails = (SysUserDetails)authentication.getPrincipal();
        Map<String, Object> payload = new HashMap<>();
        payload.put(JwtClaimConstants.USER_ID, userDetails.getUserId()); // 用户ID
        payload.put(JwtClaimConstants.DEPT_ID, userDetails.getDeptId()); // 部门ID
        payload.put(JwtClaimConstants.DATA_SCOPE, userDetails.getDataScope()); // 数据权限范围
        // claims 中添加角色信息
        Set<String> roles =
            userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        payload.put(JwtClaimConstants.AUTHORITIES, roles);
        Date now = new Date();
        payload.put(JWTPayload.ISSUED_AT, now);
        // 设置过期时间 -1 表示永不过期
        if (ttl != -1) {
            Date expiration = DateUtil.offsetSecond(now, ttl);
            payload.put(JWTPayload.EXPIRES_AT, expiration);
        }
        payload.put(JWTPayload.SUBJECT, authentication.getName());
        payload.put(JWTPayload.JWT_ID, IdUtil.simpleUUID());
        return JWTUtil.createToken(payload, key);
    }

    /**
     * 从 JWT Token 中解析 Authentication  用户认证信息
     *
     * @param payloads JWT 载体
     * @return org.springframework.security.authentication.UsernamePasswordAuthenticationToken 用户认证信息
     * @author sichu huang
     * @since 2024/10/16 23:45:23
     */
    public static UsernamePasswordAuthenticationToken getAuthentication(JSONObject payloads) {
        SysUserDetails userDetails = new SysUserDetails();
        userDetails.setUserId(payloads.getLong(JwtClaimConstants.USER_ID)); // 用户ID
        userDetails.setDeptId(payloads.getLong(JwtClaimConstants.DEPT_ID)); // 部门ID
        userDetails.setDataScope(payloads.getInt(JwtClaimConstants.DATA_SCOPE)); // 数据权限范围
        userDetails.setUsername(payloads.getStr(JWTPayload.SUBJECT)); // 用户名
        // 角色集合
        Set<SimpleGrantedAuthority> authorities =
            payloads.getJSONArray(JwtClaimConstants.AUTHORITIES).stream()
                .map(authority -> new SimpleGrantedAuthority(Convert.toStr(authority)))
                .collect(Collectors.toSet());

        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

    @Value("${security.jwt.key}")
    public void setKey(String key) {
        JwtUtils.key = key.getBytes();
    }

    @Value("${security.jwt.ttl}")
    public void setTtl(Integer ttl) {
        JwtUtils.ttl = ttl;
    }
}
