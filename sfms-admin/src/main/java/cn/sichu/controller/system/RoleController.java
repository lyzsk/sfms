package cn.sichu.controller.system;

import cn.sichu.crud.core.annotation.CrudRequestMapping;
import cn.sichu.crud.core.enums.Api;
import cn.sichu.crud.mp.controller.BaseController;
import cn.sichu.system.model.query.RoleQuery;
import cn.sichu.system.model.req.RoleReq;
import cn.sichu.system.model.resp.RoleDetailResp;
import cn.sichu.system.model.resp.RoleResp;
import cn.sichu.system.service.IRoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色管理 API
 *
 * @author sichu huang
 * @date 2024/10/13
 **/
@Tag(name = "角色管理 API")
@RestController
@CrudRequestMapping(value = "/system/role",
    api = {Api.PAGE, Api.GET, Api.ADD, Api.UPDATE, Api.DELETE})
public class RoleController
    extends BaseController<IRoleService, RoleResp, RoleDetailResp, RoleQuery, RoleReq> {
}
