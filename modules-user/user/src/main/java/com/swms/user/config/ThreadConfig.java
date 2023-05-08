package com.swms.user.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * @author sws
 * @version 1.0
 */
@Configuration
@Slf4j
public class ThreadConfig {
    @Bean("system")
    public ExecutorService executorService() {
        return new ThreadPoolExecutor(3, 3, 0L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(1024), new ThreadFactoryBuilder()
            .setNameFormat("SystemExecutor-%d")
            .setDaemon(false)
            .setUncaughtExceptionHandler(new LogUncaughtExceptionHandler(log))
            .build());
    }

    public record LogUncaughtExceptionHandler(Logger log) implements Thread.UncaughtExceptionHandler {

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            log.error("Thread {} failed by not catching exception: {}.", t.getName(), e);
        }
    }

}
