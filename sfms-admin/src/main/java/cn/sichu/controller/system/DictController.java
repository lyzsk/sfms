package cn.sichu.controller.system;

import cn.sichu.crud.core.annotation.CrudRequestMapping;
import cn.sichu.crud.core.enums.Api;
import cn.sichu.crud.mp.controller.BaseController;
import cn.sichu.system.model.query.DictQuery;
import cn.sichu.system.model.req.DictReq;
import cn.sichu.system.model.resp.DictResp;
import cn.sichu.system.service.IDictService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RestController;

/**
 * 字典管理 API
 *
 * @author sichu huang
 * @date 2024/10/13
 **/
@Tag(name = "字典管理 API")
@RestController
@CrudRequestMapping(value = "/system/dict",
    api = {Api.LIST, Api.GET, Api.ADD, Api.UPDATE, Api.DELETE})
public class DictController
    extends BaseController<IDictService, DictResp, DictResp, DictQuery, DictReq> {
}