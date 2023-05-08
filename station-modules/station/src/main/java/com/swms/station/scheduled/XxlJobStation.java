package com.swms.station.scheduled;

import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * station测试xxljob定时任务
 *
 * @author krystal
 * @date 2023/05/08
 */
@Slf4j
@Component
public class XxlJobStation {

    @XxlJob(value = "xxlJobStation")
    public void execute() throws Exception {
        log.info("XxlJobStation execute");
    }
}
