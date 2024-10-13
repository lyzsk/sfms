package cn.sichu.controller.system;

import cn.sichu.crud.core.model.query.PageQuery;
import cn.sichu.crud.mp.model.resp.PageResp;
import cn.sichu.log.annotation.Log;
import cn.sichu.system.model.query.MessageQuery;
import cn.sichu.system.model.resp.MessageResp;
import cn.sichu.system.model.resp.MessageUnreadResp;
import cn.sichu.system.service.IMessageService;
import cn.sichu.system.service.IMessageUserService;
import cn.sichu.utils.helper.LoginHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

/**
 * 消息管理 API
 *
 * @author sichu huang
 * @date 2024/10/13
 **/
@Tag(name = "消息管理 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/message")
public class MessageController {

    private final IMessageService baseService;
    private final IMessageUserService messageUserService;

    @Operation(summary = "分页查询列表", description = "分页查询列表")
    @GetMapping
    public PageResp<MessageResp> page(MessageQuery query, @Validated PageQuery pageQuery) {
        query.setUserId(LoginHelper.getUserId());
        return baseService.page(query, pageQuery);
    }

    @Operation(summary = "删除数据", description = "删除数据")
    @Parameter(name = "ids", description = "ID 列表", example = "1,2", in = ParameterIn.PATH)
    @DeleteMapping("/{ids}")
    public void delete(@PathVariable List<Long> ids) {
        baseService.delete(ids);
    }

    @Operation(summary = "标记已读", description = "将消息标记为已读状态")
    @Parameter(name = "ids", description = "消息ID列表", example = "1,2", in = ParameterIn.QUERY)
    @PatchMapping("/read")
    public void readMessage(@RequestParam(required = false) List<Long> ids) throws ParseException {
        messageUserService.readMessage(ids);
    }

    @Log(ignore = true)
    @Operation(summary = "查询未读消息数量", description = "查询当前用户的未读消息数量")
    @Parameter(name = "isDetail", description = "是否查询详情", example = "true",
        in = ParameterIn.QUERY)
    @GetMapping("/unread")
    public MessageUnreadResp countUnreadMessage(@RequestParam(required = false) Boolean detail) {
        return messageUserService.countUnreadMessageByUserId(LoginHelper.getUserId(), detail);
    }
}