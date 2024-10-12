package cn.sichu.auth.model.resp;

import cn.crane4j.annotation.Assemble;
import cn.crane4j.annotation.AssembleMethod;
import cn.crane4j.annotation.ContainerMethod;
import cn.crane4j.annotation.MappingType;
import cn.sichu.auth.service.IOnlineUserService;
import cn.sichu.constant.ContainerConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 在线用户信息
 *
 * @author sichu huang
 * @date 2024/10/10
 **/
@Data
@Schema(description = "在线用户信息")
public class OnlineUserResp implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     **/
    @Schema(description = "ID", example = "1")
    @Assemble(prop = ":nickname", container = ContainerConstants.USER_NICKNAME)
    private Long id;

    /**
     * 令牌
     **/
    @Schema(description = "令牌",
        example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJsb2dpblR5cGUiOiJsb2dpbiIsImxvZ2luSWQiOjEsInJuU3RyIjoiTUd6djdyOVFoeHEwdVFqdFAzV3M5YjVJRzh4YjZPSEUifQ.7q7U3ouoN7WPhH2kUEM7vPe5KF3G_qavSG-vRgIxKvE")
    @AssembleMethod(prop = ":lastActiveTime", targetType = IOnlineUserService.class,
        method = @ContainerMethod(bindMethod = "getLastActiveTime",
            type = MappingType.ORDER_OF_KEYS))
    private String token;

    /**
     * 用户名
     **/
    @Schema(description = "用户名", example = "zhangsan")
    private String username;

    /**
     * 昵称
     **/
    @Schema(description = "昵称", example = "张三")
    private String nickname;

    /**
     * 登录IP
     **/
    @Schema(description = "登录 IP", example = "")
    private String ip;

    /**
     * 登录地点
     **/
    @Schema(description = "登录地点", example = "中国北京北京市")
    private String address;

    /**
     * 浏览器
     **/
    @Schema(description = "浏览器", example = "Chrome 115.0.0.0")
    private String browser;

    /**
     * 操作系统
     **/
    @Schema(description = "操作系统", example = "Windows 10")
    private String os;

    /**
     * 登录时间
     **/
    @Schema(description = "登录时间", example = "2023-08-08 08:08:08.000", type = "string")
    private Date loginTime;

    /**
     * 最后活跃时间
     **/
    @Schema(description = "最后活跃时间", example = "2023-08-08 08:08:08.000", type = "string")
    private Date lastActiveTime;
}
