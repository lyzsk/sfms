package cn.sichu.config;

import cn.sichu.system.core.handler.MyDataPermissionHandler;
import cn.sichu.system.core.handler.MyMetaObjectHandler;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DataPermissionInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * mybatis-plus 自动配置类
 *
 * @author sichu huang
 * @since 2024/10/17 16:36
 */
@Configuration
@EnableTransactionManagement
public class MybatisConfig {

    /**
     * 分页插件和数据权限插件
     *
     * @return com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor
     * @author sichu huang
     * @since 2024/10/17 16:38:11
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //数据权限
        interceptor.addInnerInterceptor(
            new DataPermissionInterceptor(new MyDataPermissionHandler()));
        //分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));

        return interceptor;
    }

    /**
     * 自动填充数据库创建人、创建时间、更新人、更新时间
     *
     * @return com.baomidou.mybatisplus.core.config.GlobalConfig
     * @author sichu huang
     * @since 2024/10/17 16:38:23
     */
    @Bean
    public GlobalConfig globalConfig() {
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setMetaObjectHandler(new MyMetaObjectHandler());
        return globalConfig;
    }
}
