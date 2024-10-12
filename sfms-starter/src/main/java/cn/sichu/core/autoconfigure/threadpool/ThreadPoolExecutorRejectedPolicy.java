package cn.sichu.core.autoconfigure.threadpool;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池拒绝策略
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public enum ThreadPoolExecutorRejectedPolicy {

    /**
     * ThreadPoolTaskExecutor 默认的拒绝策略，不执行新任务，直接抛出 RejectedExecutionException 异常
     */
    ABORT {
        @Override
        public RejectedExecutionHandler getRejectedExecutionHandler() {
            return new ThreadPoolExecutor.AbortPolicy();
        }
    },

    /**
     * 提交的任务在执行被拒绝时，会由提交任务的线程去执行
     */
    CALLER_RUNS {
        @Override
        public RejectedExecutionHandler getRejectedExecutionHandler() {
            return new ThreadPoolExecutor.CallerRunsPolicy();
        }
    },

    /**
     * 不执行新任务，也不抛出异常
     */
    DISCARD {
        @Override
        public RejectedExecutionHandler getRejectedExecutionHandler() {
            return new ThreadPoolExecutor.DiscardPolicy();
        }
    },

    /**
     * 拒绝新任务，但是会抛弃队列中最老的任务，然后尝试再次提交新任务
     */
    DISCARD_OLDEST {
        @Override
        public RejectedExecutionHandler getRejectedExecutionHandler() {
            return new ThreadPoolExecutor.DiscardOldestPolicy();
        }
    };

    /**
     * 获取拒绝处理器
     *
     * @return 拒绝处理器
     */
    public abstract RejectedExecutionHandler getRejectedExecutionHandler();
}
