package cn.sichu.controller.system;

import cn.sichu.crud.core.annotation.CrudRequestMapping;
import cn.sichu.crud.core.enums.Api;
import cn.sichu.crud.mp.controller.BaseController;
import cn.sichu.system.model.query.StorageQuery;
import cn.sichu.system.model.req.StorageReq;
import cn.sichu.system.model.resp.StorageResp;
import cn.sichu.system.service.IStorageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RestController;

/**
 * 存储管理 API
 *
 * @author sichu huang
 * @date 2024/10/13
 **/
@Tag(name = "存储管理 API")
@RestController
@CrudRequestMapping(value = "/system/storage",
    api = {Api.PAGE, Api.GET, Api.ADD, Api.UPDATE, Api.DELETE})
public class StorageController
    extends BaseController<IStorageService, StorageResp, StorageResp, StorageQuery, StorageReq> {
}