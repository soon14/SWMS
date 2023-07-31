package com.swms.wms;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = {"com.swms"})
@EnableDiscoveryClient
public class WmsApplicationTest {

    public static void main(String[] args) {
        SpringApplication.run(WmsApplicationTest.class, args);
    }

    @Test
    void test() {
        Assertions.assertTrue(true);
    }
}
