package cn.sichu.system.model.entity;

import cn.sichu.crud.mp.model.entity.BaseDO;
import cn.sichu.enums.DisEnableStatusEnum;
import cn.sichu.system.enums.MenuTypeEnum;
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
@TableName("t_sys_menu")
public class MenuDO extends BaseDO {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 标题
     **/
    private String title;

    /**
     * 上级菜单ID
     **/
    private Long parentId;

    /**
     * 类型
     **/
    private MenuTypeEnum type;

    /**
     * 路由地址
     **/
    private String path;

    /**
     * 组件名称
     **/
    private String name;

    /**
     * 组件路径
     **/
    private String component;

    /**
     * 重定向地址
     **/
    private String redirect;

    /**
     * 图标
     **/
    private String icon;

    /**
     * 是否外链
     **/
    private Boolean isExternal;

    /**
     * 是否缓存
     **/
    private Boolean isCache;

    /**
     * 是否隐藏
     **/
    private Boolean isHidden;

    /**
     * 权限标识
     **/
    private String permission;

    /**
     * 排序
     **/
    private Integer sort;

    /**
     * 状态
     **/
    private DisEnableStatusEnum status;
}
