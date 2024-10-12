package cn.sichu.config.mybatis;

import cn.sichu.data.mp.datapermission.DataPermissionFilter;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * MyBatis Plus 配置
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Configuration
public class MybatisPlusConfiguration {

    /**
     * 元对象处理器配置（插入或修改时自动填充）
     *
     * @return com.baomidou.mybatisplus.core.handlers.MetaObjectHandler
     * @author sichu huang
     * @date 2024/10/12
     **/
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new MyBatisPlusMetaObjectHandler();
    }

    /**
     * 数据权限过滤器
     *
     * @return cn.sichu.data.mf.datapermission.DataPermissionFilter
     * @author sichu huang
     * @date 2024/10/12
     **/
    @Bean
    public DataPermissionFilter dataPermissionFilter() {
        return new DataPermissionFilterImpl();
    }

    /**
     * BCrypt 加/解密处理器
     *
     * @param passwordEncoder passwordEncoder
     * @return common.config.mybatis.BCryptEncryptor
     * @author sichu huang
     * @date 2024/10/12
     **/
    @Bean
    public BCryptEncryptor bCryptEncryptor(PasswordEncoder passwordEncoder) {
        return new BCryptEncryptor(passwordEncoder);
    }
}
