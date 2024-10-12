package cn.sichu.system.model.resp;

import cn.sichu.crud.core.model.resp.BaseDetailResp;
import cn.sichu.enums.DisEnableStatusEnum;
import cn.sichu.security.mask.annotation.JsonMask;
import cn.sichu.system.enums.StorageTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 存储响应信息
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "存储响应信息")
public class StorageResp extends BaseDetailResp {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 名称
     **/
    @Schema(description = "名称", example = "存储1")
    private String name;

    /**
     * 编码
     **/
    @Schema(description = "编码", example = "local")
    private String code;

    /**
     * 状态
     **/
    @Schema(description = "状态", example = "1")
    private DisEnableStatusEnum status;

    /**
     * 类型
     **/
    @Schema(description = "类型", example = "2")
    private StorageTypeEnum type;

    /**
     * 访问密钥
     **/
    @Schema(description = "访问密钥", example = "")
    private String accessKey;

    /**
     * 私有密钥
     **/
    @JsonMask(left = 4, right = 3)
    private String secretKey;

    /**
     * 终端节点
     **/
    @Schema(description = "终端节点", example = "")
    private String endpoint;

    /**
     * 桶名称
     **/
    @Schema(description = "桶名称", example = "C:/sfms/data/file/")
    private String bucketName;

    /**
     * 域名
     **/
    @Schema(description = "域名", example = "http://localhost:8000/file")
    private String domain;

    /**
     * 描述
     **/
    @Schema(description = "描述", example = "存储描述")
    private String description;

    /**
     * 是否为默认存储
     **/
    @Schema(description = "是否为默认存储", example = "true")
    private Boolean isDefault;

    /**
     * 排序
     **/
    @Schema(description = "排序", example = "1")
    private Integer sort;

    @Override
    public Boolean getDisabled() {
        return this.getIsDefault();
    }
}
