package cn.sichu.config.log;

import cn.sichu.log.autoconfigure.ConditionalOnEnabledLog;
import cn.sichu.log.dao.LogDAO;
import cn.sichu.system.mapper.LogMapper;
import cn.sichu.system.service.IUserService;
import cn.sichu.web.autoconfigure.trace.TraceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 日志配置
 *
 * @author sichu huang
 * @date 2024/10/13
 **/
@Configuration
@ConditionalOnEnabledLog
public class LogConfiguration {

    /**
     * 日志持久层接口本地实现类
     *
     * @param userService     userService
     * @param logMapper       logMapper
     * @param traceProperties traceProperties
     * @return cn.sichu.log.dao.LogDAO
     * @author sichu huang
     * @date 2024/10/13
     **/
    @Bean
    public LogDAO logDao(IUserService userService, LogMapper logMapper,
        TraceProperties traceProperties) {
        return new LogDAOLocalImpl(userService, logMapper, traceProperties);
    }
}
