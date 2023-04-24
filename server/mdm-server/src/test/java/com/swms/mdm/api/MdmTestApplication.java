package com.swms.mdm.api;

import com.alibaba.cloud.nacos.NacosConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration
@SpringBootApplication(scanBasePackages = {"com.swms"})
@EnableConfigurationProperties(NacosConfigProperties.class)
public class MdmTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(MdmTestApplication.class, args);
    }
}
