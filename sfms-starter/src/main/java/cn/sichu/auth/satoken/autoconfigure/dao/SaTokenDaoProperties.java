package cn.sichu.auth.satoken.autoconfigure.dao;

import cn.sichu.auth.satoken.enums.SaTokenDaoType;
import lombok.Getter;
import lombok.Setter;

/**
 * SaToken 持久层配置属性
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Setter
@Getter
public class SaTokenDaoProperties {

    /**
     * 持久层类型
     */
    private SaTokenDaoType type = SaTokenDaoType.DEFAULT;

}
