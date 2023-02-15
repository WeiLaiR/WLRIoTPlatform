package com.weilai.atestsendmessage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@EnableAsync
@Configuration
public class CustomThreadPool {

    @Bean
    public ThreadPoolTaskExecutor customThreadPool1 () {
        ThreadPoolTaskExecutor threadPoll = new ThreadPoolTaskExecutor();
        // 设置核心线程数
        threadPoll.setCorePoolSize(30);
        // 设置最大线程数
        threadPoll.setMaxPoolSize(50);
        // 设置队列大小
        threadPoll.setQueueCapacity(100);
        // 设置线程活跃时间(秒)
        threadPoll.setKeepAliveSeconds(60);
        // 设置线程名前缀+分组名称
        threadPoll.setThreadNamePrefix("Thread-");
        threadPoll.setThreadGroupName("Group");
        //设置拒绝策略
        threadPoll.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        // 所有任务结束后关闭线程池
        threadPoll.setWaitForTasksToCompleteOnShutdown(true);
        // 初始化
        threadPoll.initialize();
        return threadPoll;
    }
}
