package com.swms.mdm;

import com.gitee.starblues.loader.DevelopmentMode;
import com.gitee.starblues.loader.launcher.SpringBootstrap;
import com.gitee.starblues.loader.launcher.SpringMainBootstrap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.swms", "com.gitee.starblues"})
public class MdmApplication implements SpringBootstrap {

    public static void main(String[] args) {
        SpringMainBootstrap.launch(MdmApplication.class, args);
    }

    @Override
    public void run(String[] args) {
        // 在该实现方法中, 和SpringBoot使用方式一致
        SpringApplication.run(MdmApplication.class, args);
    }

    @Override
    public String developmentMode() {
        // 指定为共享模式
        return DevelopmentMode.COEXIST;
    }
}
