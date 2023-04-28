package com.swms.wms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.TestConfiguration;

@SpringBootApplication(scanBasePackages = {"com.swms"})
public class WmsApplicationTest {
    public static void main(String[] args) {
        SpringApplication.run(WmsApplicationTest.class, args);
    }
}
