package cn.sichu.system.handler;

import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * xxl-job 测试示例（Bean模式）
 *
 * @author sichu huang
 * @since 2024/10/17 15:40
 */
@Slf4j
@Component
public class XxlJobSampleHandler {

    @XxlJob("demoJobHandler")
    public void demoJobHandler() {
        log.info("XXL-JOB, Hello World.");
    }
}
