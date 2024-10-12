package cn.sichu.core.autoconfigure.threadpool;

import cn.hutool.core.util.ArrayUtil;
import cn.sichu.core.constant.PropertiesConstants;
import cn.sichu.core.exception.BaseException;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Arrays;
import java.util.concurrent.Executor;

/**
 * 异步任务自动配置
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Lazy
@AutoConfiguration
@EnableAsync(proxyTargetClass = true)
@ConditionalOnProperty(prefix = "spring.task.execution.extension",
    name = PropertiesConstants.ENABLED, havingValue = "true", matchIfMissing = true)
public class AsyncAutoConfiguration implements AsyncConfigurer {

    private static final Logger log = LoggerFactory.getLogger(AsyncAutoConfiguration.class);

    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;

    public AsyncAutoConfiguration(ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
    }

    /**
     * 异步任务线程池配置
     */
    @Override
    public Executor getAsyncExecutor() {
        return threadPoolTaskExecutor;
    }

    /**
     * 异步任务执行时的异常处理
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (throwable, method, objects) -> {
            throwable.printStackTrace();
            StringBuilder sb = new StringBuilder();
            sb.append("Exception message: ").append(throwable.getMessage())
                .append(", Method name: ").append(method.getName());
            if (ArrayUtil.isNotEmpty(objects)) {
                sb.append(", Parameter value: ").append(Arrays.toString(objects));
            }
            throw new BaseException(sb.toString());
        };
    }

    @PostConstruct
    public void postConstruct() {
        log.debug("[SFMS] - Auto Configuration 'AsyncConfigurer' completed initialization.");
    }
}
