package com.swms.mdm;

import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.swms"})
public class MdmApplication {

    public static void main(String[] args) {
        SpringApplication.run(MdmApplication.class, args);
    }
}
