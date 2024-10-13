package cn.sichu.controller.system;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.sichu.core.constant.StringConstants;
import cn.sichu.core.utils.URLUtils;
import cn.sichu.core.utils.validate.ValidationUtils;
import cn.sichu.crud.core.annotation.CrudRequestMapping;
import cn.sichu.crud.core.enums.Api;
import cn.sichu.crud.core.model.resp.BaseIdResp;
import cn.sichu.crud.core.utils.ValidateGroup;
import cn.sichu.crud.mp.controller.BaseController;
import cn.sichu.system.model.query.MenuQuery;
import cn.sichu.system.model.req.MenuReq;
import cn.sichu.system.model.resp.MenuResp;
import cn.sichu.system.service.IMenuService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 菜单管理 API
 *
 * @author sichu huang
 * @date 2024/10/13
 **/
@Tag(name = "菜单管理 API")
@RestController
@CrudRequestMapping(value = "/system/menu",
    api = {Api.TREE, Api.GET, Api.ADD, Api.UPDATE, Api.DELETE})
public class MenuController
    extends BaseController<IMenuService, MenuResp, MenuResp, MenuQuery, MenuReq> {

    @Override
    public BaseIdResp<Long> add(@Validated(ValidateGroup.Crud.Add.class) @RequestBody MenuReq req) {
        this.checkPath(req);
        return super.add(req);
    }

    @Override
    public void update(@Validated(ValidateGroup.Crud.Update.class) @RequestBody MenuReq req,
        @PathVariable Long id) {
        this.checkPath(req);
        super.update(req, id);
    }

    /**
     * 检查路由地址格式
     *
     * @param req 创建或修改信息
     * @author sichu huang
     * @date 2024/10/13
     **/
    private void checkPath(MenuReq req) {
        Boolean isExternal = ObjectUtil.defaultIfNull(req.getIsExternal(), false);
        String path = req.getPath();
        ValidationUtils.throwIf(Boolean.TRUE.equals(isExternal) && !URLUtils.isHttpUrl(path),
            "路由地址格式错误，请以 http:// 或 https:// 开头");
        // 非外链菜单参数修正
        if (Boolean.FALSE.equals(isExternal)) {
            ValidationUtils.throwIf(URLUtils.isHttpUrl(path), "路由地址格式错误");
            req.setPath(StrUtil.isBlank(path) ? path :
                StrUtil.prependIfMissing(path, StringConstants.SLASH));
            req.setName(StrUtil.removePrefix(req.getName(), StringConstants.SLASH));
            req.setComponent(StrUtil.removePrefix(req.getComponent(), StringConstants.SLASH));
        }
    }
}
