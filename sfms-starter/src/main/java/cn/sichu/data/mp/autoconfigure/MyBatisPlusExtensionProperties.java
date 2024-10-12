package cn.sichu.data.mp.autoconfigure;

import cn.sichu.data.mp.autoconfigure.idgenerator.MyBatisPlusIdGeneratorProperties;
import com.baomidou.mybatisplus.annotation.DbType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * MyBatis Plus 扩展配置属性
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Setter
@Getter
@ConfigurationProperties("mybatis-plus.extension")
public class MyBatisPlusExtensionProperties {

    /**
     * 是否启用扩展
     */
    private boolean enabled = false;

    /**
     * Mapper 接口扫描包（配置时必须使用：mapper-package 键名）
     * <p>
     * e.g. com.example.**.mapper
     * </p>
     */
    private String mapperPackage;

    /**
     * ID 生成器
     */
    @NestedConfigurationProperty
    private MyBatisPlusIdGeneratorProperties idGenerator;

    /**
     * 分页插件配置
     */
    private PaginationProperties pagination;

    /**
     * 启用乐观锁插件
     */
    private boolean optimisticLockerEnabled = false;

    /**
     * 启用防全表更新与删除插件
     */
    private boolean blockAttackPluginEnabled = true;

    /**
     * 分页插件配置属性
     */
    public static class PaginationProperties {

        /**
         * 是否启用分页插件
         */
        private boolean enabled = true;

        /**
         * 数据库类型
         */
        private DbType dbType;

        /**
         * 是否溢出处理
         */
        private boolean overflow = false;

        /**
         * 单页分页条数限制（默认：-1 表示无限制）
         */
        private Long maxLimit = -1L;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public DbType getDbType() {
            return dbType;
        }

        public void setDbType(DbType dbType) {
            this.dbType = dbType;
        }

        public boolean isOverflow() {
            return overflow;
        }

        public void setOverflow(boolean overflow) {
            this.overflow = overflow;
        }

        public Long getMaxLimit() {
            return maxLimit;
        }

        public void setMaxLimit(Long maxLimit) {
            this.maxLimit = maxLimit;
        }
    }
}
