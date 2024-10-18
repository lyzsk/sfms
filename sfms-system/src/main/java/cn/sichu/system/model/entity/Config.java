package cn.sichu.system.model.entity;

import cn.sichu.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统配置 实体
 *
 * @author sichu huang
 * @since 2024/10/16 22:31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统配置")
@TableName("sys_config")
public class Config extends BaseEntity {
    
    /**
     * 配置名称
     */
    private String configName;

    /**
     * 配置键
     */
    private String configKey;

    /**
     * 配置值
     */
    private String configValue;
}
