package com.swms.wms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.swms.**"})
public class WmsApplicationTest {
    public static void main(String[] args) {
        SpringApplication.run(WmsApplicationTest.class, args);
    }
}
