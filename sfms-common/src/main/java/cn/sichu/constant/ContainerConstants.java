package cn.sichu.constant;

import cn.sichu.crud.core.constant.ContainerPool;

/**
 * 数据源容器相关常量（Crane4j 数据填充组件使用）
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public class ContainerConstants extends ContainerPool {

    /**
     * 用户昵称
     **/
    public static final String USER_NICKNAME = ContainerPool.USER_NICKNAME;

    /**
     * 用户角色 ID 列表
     **/
    public static final String USER_ROLE_ID_LIST = "UserRoleIdList";

    /**
     * 用户角色名称列表
     **/
    public static final String USER_ROLE_NAME_LIST = "UserRoleNameList";

    private ContainerConstants() {
    }
}