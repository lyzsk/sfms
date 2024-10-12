package cn.sichu.auth.satoken.autoconfigure;

import lombok.Getter;
import lombok.Setter;

/**
 * SaToken 安全配置属性
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Setter
@Getter
public class SaTokenSecurityProperties {

    /**
     * 排除（放行）路径配置
     */
    private String[] excludes = new String[0];

}