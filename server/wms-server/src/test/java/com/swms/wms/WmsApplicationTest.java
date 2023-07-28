package com.swms.wms;

import com.alibaba.cloud.commons.io.FileUtils;
import com.swms.tenant.api.ITenantApi;
import com.swms.tenant.api.dto.TenantDTO;
import com.swms.utils.utils.JsonUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

@SpringBootApplication(scanBasePackages = {"com.swms"})
@EnableDiscoveryClient
public class WmsApplicationTest {

    public static void main(String[] args) {
        SpringApplication.run(WmsApplicationTest.class, args);
    }

    @Bean("mockITenantApi")
    public ITenantApi iTenantApi() throws IOException {
        File file = ResourceUtils.getFile("classpath:json/AllTenants.json");
        String s = FileUtils.readFileToString(file, "UTF-8");
        List<TenantDTO> allTenants = JsonUtils.string2List(s, TenantDTO.class);

        ITenantApi iTenantApi = PowerMockito.mock(ITenantApi.class);
        PowerMockito.when(iTenantApi.findAll()).thenAnswer(t -> allTenants);


        return iTenantApi;
    }

    @Test
    void test() {
        Assertions.assertTrue(true);
    }
}
