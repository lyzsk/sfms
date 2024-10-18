package cn.sichu.system.model.entity;

import cn.sichu.base.BaseEntity;
import cn.sichu.system.enums.MenuTypeEnum;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;

/**
 * 菜单实体
 *
 * @author sichu huang
 * @since 2024/10/16 22:35
 */
@Getter
@Setter
@TableName("sys_menu")
public class Menu extends BaseEntity {

    /**
     * 菜单ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 父节点路径，以英文逗号(,)分割
     */
    private String treePath;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单类型(1-菜单；2-目录；3-外链；4-按钮权限)
     */
    private MenuTypeEnum type;

    /**
     * 路由名称（Vue Router 中定义的路由名称）
     */
    private String routeName;

    /**
     * 路由路径（Vue Router 中定义的 URL 路径）
     */
    private String routePath;

    /**
     * 组件路径(vue页面完整路径，省略.vue后缀)
     */
    private String component;

    /**
     * 权限标识
     */
    private String perm;

    /**
     * 【菜单】是否开启页面缓存(1:开启;0:关闭)
     */
    private Integer keepAlive;

    /**
     * 【目录】只有一个子路由是否始终显示(1:是 0:否)
     */
    private Integer alwaysShow;

    /**
     * 显示状态(1:显示;0:隐藏)
     */
    private Integer visible;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 跳转路径
     */
    private String redirect;

    /**
     * 路由参数
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private String params;
}