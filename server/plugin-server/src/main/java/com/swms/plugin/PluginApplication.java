package com.swms.plugin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.swms"})
public class PluginApplication {

    public static void main(String[] args) {
        SpringApplication.run(PluginApplication.class, args);
    }

}
