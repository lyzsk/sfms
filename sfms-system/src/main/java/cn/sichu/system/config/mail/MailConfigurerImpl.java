package cn.sichu.system.config.mail;

import cn.hutool.core.map.MapUtil;
import cn.sichu.constant.SysConstants;
import cn.sichu.messaging.mail.core.MailConfig;
import cn.sichu.messaging.mail.core.MailConfigurer;
import cn.sichu.system.service.IOptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 邮件配置实现
 *
 * @author sichu huang
 * @date 2024/10/10
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class MailConfigurerImpl implements MailConfigurer {

    private final IOptionService optionService;

    @Override
    public MailConfig getMailConfig() {
        // 查询邮件配置
        Map<String, String> map = optionService.getByCategory("MAIL");
        // 封装邮件配置
        MailConfig mailConfig = new MailConfig();
        mailConfig.setProtocol(MapUtil.getStr(map, "MAIL_PROTOCOL"));
        mailConfig.setHost(MapUtil.getStr(map, "MAIL_HOST"));
        mailConfig.setPort(MapUtil.getInt(map, "MAIL_PORT"));
        mailConfig.setUsername(MapUtil.getStr(map, "MAIL_USERNAME"));
        mailConfig.setPassword(MapUtil.getStr(map, "MAIL_PASSWORD"));
        mailConfig.setSslEnabled(SysConstants.YES.equals(MapUtil.getInt(map, "MAIL_SSL_ENABLED")));
        if (mailConfig.isSslEnabled()) {
            mailConfig.setSslPort(MapUtil.getInt(map, "MAIL_SSL_PORT"));
        }
        return mailConfig;
    }
}
