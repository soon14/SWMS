package com.swms.domain.event.config;

import com.alibaba.ttl.threadpool.TtlExecutors;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;
import com.swms.common.utils.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@Slf4j
public class DomainEventConfiguration {

    private static final int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private static final int MAX_POOL_SIZE = CORE_POOL_SIZE * 2 + 1;

    @Bean
    public Executor asyncEventBusExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(11);
        executor.setMaxPoolSize(MAX_POOL_SIZE * 4);
        executor.setQueueCapacity(256);
        executor.setThreadNamePrefix("async-event-bus-executor");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return TtlExecutors.getTtlExecutor(executor);
    }

    @Bean("asyncEventBus")
    public AsyncEventBus asyncEventBus() {
        return new AsyncEventBus(asyncEventBusExecutor(), new DomainEventConfiguration.CustomSubscriberExceptionHandler());
    }

    @Bean("syncEventBus")
    public EventBus syncEventBus() {
        return new EventBus(new DomainEventConfiguration.CustomSubscriberExceptionHandler());
    }

    public static class CustomSubscriberExceptionHandler implements SubscriberExceptionHandler {
        @Override
        public void handleException(Throwable exception, SubscriberExceptionContext context) {
            log.error("exception occurred while handling event: {} with method: {}.",
                JsonUtils.obj2String(context.getEvent()), context.getSubscriberMethod().getName(), exception);
        }
    }
}
