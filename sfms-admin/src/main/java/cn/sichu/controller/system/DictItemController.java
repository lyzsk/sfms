package cn.sichu.controller.system;

import cn.sichu.crud.core.annotation.CrudRequestMapping;
import cn.sichu.crud.core.enums.Api;
import cn.sichu.crud.mp.controller.BaseController;
import cn.sichu.system.model.query.DictItemQuery;
import cn.sichu.system.model.req.DictItemReq;
import cn.sichu.system.model.resp.DictItemResp;
import cn.sichu.system.service.IDictItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RestController;

/**
 * 字典项管理 API
 *
 * @author sichu huang
 * @date 2024/10/13
 **/
@Tag(name = "字典项管理 API")
@RestController
@CrudRequestMapping(value = "/system/dict/item",
    api = {Api.PAGE, Api.GET, Api.ADD, Api.UPDATE, Api.DELETE})
public class DictItemController extends
    BaseController<IDictItemService, DictItemResp, DictItemResp, DictItemQuery, DictItemReq> {
}