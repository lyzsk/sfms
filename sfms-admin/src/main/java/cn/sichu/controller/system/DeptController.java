package cn.sichu.controller.system;

import cn.sichu.crud.core.annotation.CrudRequestMapping;
import cn.sichu.crud.core.enums.Api;
import cn.sichu.crud.mp.controller.BaseController;
import cn.sichu.system.model.query.DeptQuery;
import cn.sichu.system.model.req.DeptReq;
import cn.sichu.system.model.resp.DeptResp;
import cn.sichu.system.service.IDeptService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RestController;

/**
 * 部门管理 API
 *
 * @author sichu huang
 * @date 2024/10/13
 **/
@Tag(name = "部门管理 API")
@RestController
@CrudRequestMapping(value = "/system/dept",
    api = {Api.TREE, Api.GET, Api.ADD, Api.UPDATE, Api.DELETE, Api.EXPORT})
public class DeptController
    extends BaseController<IDeptService, DeptResp, DeptResp, DeptQuery, DeptReq> {
}
