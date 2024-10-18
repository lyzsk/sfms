package cn.sichu.websocket.handler;

import cn.sichu.websocket.service.OnlineUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 在线用户定时任务
 *
 * @author sichu huang
 * @since 2024/10/17 16:05
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OnlineUserJobHandler {
    private final OnlineUserService onlineUserService;
    private final SimpMessagingTemplate messagingTemplate;

    // 每分钟统计一次在线用户数
    @Scheduled(cron = "0 * * * * ?")
    public void execute() {
        log.info("定时任务：统计在线用户数");
        // 推送在线用户人数
        messagingTemplate.convertAndSend("/topic/onlineUserCount",
            onlineUserService.getOnlineUserCount());
    }
}
