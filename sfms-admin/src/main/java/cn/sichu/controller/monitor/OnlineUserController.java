package cn.sichu.controller.monitor;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import cn.sichu.auth.model.query.OnlineUserQuery;
import cn.sichu.auth.model.resp.OnlineUserResp;
import cn.sichu.auth.service.IOnlineUserService;
import cn.sichu.core.utils.validate.CheckUtils;
import cn.sichu.crud.core.model.query.PageQuery;
import cn.sichu.crud.mp.model.resp.PageResp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 在线用户 API
 *
 * @author sichu huang
 * @date 2024/10/13
 **/
@Tag(name = "在线用户 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/monitor/online")
public class OnlineUserController {

    private final IOnlineUserService baseService;

    @Operation(summary = "分页查询列表", description = "分页查询列表")
    @SaCheckPermission("monitor:online:list")
    @GetMapping
    public PageResp<OnlineUserResp> page(OnlineUserQuery query, @Validated PageQuery pageQuery) {
        return baseService.page(query, pageQuery);
    }

    @Operation(summary = "强退在线用户", description = "强退在线用户")
    @Parameter(name = "token", description = "令牌",
        example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJsb2dpblR5cGUiOiJsb2dpbiIsImxvZ2luSWQiOjEsInJuU3RyIjoiTUd6djdyOVFoeHEwdVFqdFAzV3M5YjVJRzh4YjZPSEUifQ.7q7U3ouoN7WPhH2kUEM7vPe5KF3G_qavSG-vRgIxKvE",
        in = ParameterIn.PATH)
    @SaCheckPermission("monitor:online:kickout")
    @DeleteMapping("/{token}")
    public void kickout(@PathVariable String token) {
        String currentToken = StpUtil.getTokenValue();
        CheckUtils.throwIfEqual(token, currentToken, "不能强退自己");
        StpUtil.kickoutByTokenValue(token);
    }
}
