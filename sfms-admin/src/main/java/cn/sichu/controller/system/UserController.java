package cn.sichu.controller.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.util.ReUtil;
import cn.sichu.constant.RegexConstants;
import cn.sichu.core.utils.ExceptionUtils;
import cn.sichu.core.utils.validate.ValidationUtils;
import cn.sichu.crud.core.annotation.CrudRequestMapping;
import cn.sichu.crud.core.enums.Api;
import cn.sichu.crud.core.model.resp.BaseIdResp;
import cn.sichu.crud.core.utils.ValidateGroup;
import cn.sichu.crud.mp.controller.BaseController;
import cn.sichu.system.model.query.UserQuery;
import cn.sichu.system.model.req.UserImportReq;
import cn.sichu.system.model.req.UserPasswordResetReq;
import cn.sichu.system.model.req.UserReq;
import cn.sichu.system.model.req.UserRoleUpdateReq;
import cn.sichu.system.model.resp.UserDetailResp;
import cn.sichu.system.model.resp.UserImportParseResp;
import cn.sichu.system.model.resp.UserImportResp;
import cn.sichu.system.model.resp.UserResp;
import cn.sichu.system.service.IUserService;
import cn.sichu.utils.SecureUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;

/**
 * 用户管理 API
 *
 * @author sichu huang
 * @date 2024/10/13
 **/
@Tag(name = "用户管理 API")
@Validated
@RestController
@RequiredArgsConstructor
@CrudRequestMapping(value = "/system/user",
    api = {Api.PAGE, Api.GET, Api.ADD, Api.UPDATE, Api.DELETE, Api.EXPORT})
public class UserController
    extends BaseController<IUserService, UserResp, UserDetailResp, UserQuery, UserReq> {

    private final IUserService userService;

    @Override
    public BaseIdResp<Long> add(@Validated(ValidateGroup.Crud.Add.class) @RequestBody UserReq req) {
        String rawPassword =
            ExceptionUtils.exToNull(() -> SecureUtils.decryptByRsaPrivateKey(req.getPassword()));
        ValidationUtils.throwIfNull(rawPassword, "密码解密失败");
        ValidationUtils.throwIf(!ReUtil.isMatch(RegexConstants.PASSWORD, rawPassword),
            "密码长度为 8-32 个字符，支持大小写字母、数字、特殊字符，至少包含字母和数字");
        req.setPassword(rawPassword);
        return super.add(req);
    }

    @Operation(summary = "下载用户导入模板", description = "下载用户导入模板")
    @SaCheckPermission("system:user:import")
    @GetMapping(value = "downloadImportUserTemplate",
        produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void downloadImportUserTemplate(HttpServletResponse response) throws IOException {
        userService.downloadImportUserTemplate(response);
    }

    @Operation(summary = "解析用户导入数据", description = "解析用户导入数据")
    @SaCheckPermission("system:user:import")
    @PostMapping(value = "parseImportUser")
    public UserImportParseResp parseImportUser(
        @NotNull(message = "文件不能为空") MultipartFile file) {
        ValidationUtils.throwIf(file::isEmpty, "文件不能为空");
        return userService.parseImportUser(file);
    }

    @Operation(summary = "导入用户", description = "导入用户")
    @SaCheckPermission("system:user:import")
    @PostMapping(value = "import")
    public UserImportResp importUser(@Validated @RequestBody UserImportReq req)
        throws ParseException {
        return userService.importUser(req);
    }

    @Operation(summary = "重置密码", description = "重置用户登录密码")
    @Parameter(name = "id", description = "ID", example = "1", in = ParameterIn.PATH)
    @SaCheckPermission("system:user:resetPwd")
    @PatchMapping("/{id}/password")
    public void resetPassword(@Validated @RequestBody UserPasswordResetReq req,
        @PathVariable Long id) {
        String rawNewPassword =
            ExceptionUtils.exToNull(() -> SecureUtils.decryptByRsaPrivateKey(req.getNewPassword()));
        ValidationUtils.throwIfNull(rawNewPassword, "新密码解密失败");
        ValidationUtils.throwIf(!ReUtil.isMatch(RegexConstants.PASSWORD, rawNewPassword),
            "密码长度为 8-32 个字符，支持大小写字母、数字、特殊字符，至少包含字母和数字");
        req.setNewPassword(rawNewPassword);
        baseService.resetPassword(req, id);
    }

    @Operation(summary = "分配角色", description = "为用户新增或移除角色")
    @Parameter(name = "id", description = "ID", example = "1", in = ParameterIn.PATH)
    @SaCheckPermission("system:user:updateRole")
    @PatchMapping("/{id}/role")
    public void updateRole(@Validated @RequestBody UserRoleUpdateReq updateReq,
        @PathVariable Long id) {
        baseService.updateRole(updateReq, id);
    }
}
