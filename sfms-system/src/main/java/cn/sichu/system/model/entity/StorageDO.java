package cn.sichu.system.model.entity;

import cn.sichu.crud.mp.model.entity.BaseDO;
import cn.sichu.enums.DisEnableStatusEnum;
import cn.sichu.security.crypto.annotation.FieldEncrypt;
import cn.sichu.system.enums.StorageTypeEnum;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * @author sichu huang
 * @date 2024/10/11
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_sys_storage")
public class StorageDO extends BaseDO {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 名称
     **/
    private String name;

    /**
     * 编码
     **/
    private String code;

    /**
     * 类型
     **/
    private StorageTypeEnum type;

    /**
     * Access Key（访问密钥）
     **/
    @FieldEncrypt
    private String accessKey;

    /**
     * Secret Key（私有密钥）
     **/
    @FieldEncrypt
    private String secretKey;

    /**
     * Endpoint（终端节点）
     **/
    private String endpoint;

    /**
     * 桶名称
     **/
    private String bucketName;

    /**
     * 域名
     **/
    private String domain;

    /**
     * 描述
     **/
    private String description;

    /**
     * 是否为默认存储
     */
    private Boolean isDefault;

    /**
     * 排序
     **/
    private Integer sort;

    /**
     * 状态
     **/
    private DisEnableStatusEnum status;
}
