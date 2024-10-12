package cn.sichu.system.model.resp;

import cn.sichu.crud.core.annotation.TreeField;
import cn.sichu.crud.core.model.resp.BaseResp;
import cn.sichu.enums.DisEnableStatusEnum;
import cn.sichu.system.enums.MenuTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 菜单信息
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@TreeField(value = "id")
@Schema(description = "菜单信息")
public class MenuResp extends BaseResp {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 标题
     **/
    @Schema(description = "标题", example = "用户管理")
    private String title;

    /**
     * 上级菜单ID
     **/
    @Schema(description = "上级菜单 ID", example = "1000")
    private Long parentId;

    /**
     * 类型
     **/
    @Schema(description = "类型", example = "2")
    private MenuTypeEnum type;

    /**
     * 路由地址
     **/
    @Schema(description = "路由地址", example = "/system/user")
    private String path;

    /**
     * 组件名称
     **/
    @Schema(description = "组件名称", example = "User")
    private String name;

    /**
     * 组件路径
     **/
    @Schema(description = "组件路径", example = "/system/user/index")
    private String component;

    /**
     * 重定向地址
     **/
    @Schema(description = "重定向地址")
    private String redirect;

    /**
     * 图标
     **/
    @Schema(description = "图标", example = "user")
    private String icon;

    /**
     * 是否外链
     **/
    @Schema(description = "是否外链", example = "false")
    private Boolean isExternal;

    /**
     * 是否缓存
     **/
    @Schema(description = "是否缓存", example = "false")
    private Boolean isCache;

    /**
     * 是否隐藏
     **/
    @Schema(description = "是否隐藏", example = "false")
    private Boolean isHidden;

    /**
     * 权限标识
     **/
    @Schema(description = "权限标识", example = "system:user:list")
    private String permission;

    /**
     * 排序
     **/
    @Schema(description = "排序", example = "1")
    private Integer sort;

    /**
     * 状态
     **/
    @Schema(description = "状态", example = "1")
    private DisEnableStatusEnum status;
}
