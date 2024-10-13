package cn.sichu.controller.system;

import cn.sichu.core.utils.validate.ValidationUtils;
import cn.sichu.crud.core.annotation.CrudRequestMapping;
import cn.sichu.crud.core.enums.Api;
import cn.sichu.crud.core.model.resp.BaseIdResp;
import cn.sichu.crud.core.utils.ValidateGroup;
import cn.sichu.crud.mp.controller.BaseController;
import cn.sichu.system.model.query.NoticeQuery;
import cn.sichu.system.model.req.NoticeReq;
import cn.sichu.system.model.resp.NoticeDetailResp;
import cn.sichu.system.model.resp.NoticeResp;
import cn.sichu.system.service.INoticeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 公告管理 API
 *
 * @author sichu huang
 * @date 2024/10/13
 **/
@Tag(name = "公告管理 API")
@RestController
@CrudRequestMapping(value = "/system/notice",
    api = {Api.PAGE, Api.GET, Api.ADD, Api.UPDATE, Api.DELETE})
public class NoticeController
    extends BaseController<INoticeService, NoticeResp, NoticeDetailResp, NoticeQuery, NoticeReq> {

    @Override
    public BaseIdResp<Long> add(
        @Validated(ValidateGroup.Crud.Add.class) @RequestBody NoticeReq req) {
        this.checkTime(req);
        return super.add(req);
    }

    @Override
    public void update(@Validated(ValidateGroup.Crud.Update.class) @RequestBody NoticeReq req,
        @PathVariable Long id) {
        this.checkTime(req);
        super.update(req, id);
    }

    /**
     * 检查时间
     *
     * @param req 创建或修改信息
     * @author sichu huang
     * @date 2024/10/13
     **/
    private void checkTime(NoticeReq req) {
        Date effectiveTime = req.getEffectiveTime();
        Date terminateTime = req.getTerminateTime();
        if (null != effectiveTime && null != terminateTime) {
            ValidationUtils.throwIf(terminateTime.before(effectiveTime),
                "终止时间必须晚于生效时间");
        }
    }
}