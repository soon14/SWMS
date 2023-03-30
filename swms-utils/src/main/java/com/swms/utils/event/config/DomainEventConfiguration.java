package com.swms.utils.event.config;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@Slf4j
public class DomainEventConfiguration {

    @Bean
    public Executor asyncEventBusExecutor() {
        return new ThreadPoolExecutor(5, 10, 60, java.util.concurrent.TimeUnit.SECONDS, new java.util.concurrent.ArrayBlockingQueue<>(400));
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
            log.info("Exception: {} occurred while handling event: {} with method: {}.",
                exception.getMessage(), context.getEvent(), context.getSubscriberMethod().getName());
        }
    }
}
