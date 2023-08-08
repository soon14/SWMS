package com.swms.wms;

import com.alibaba.cloud.commons.io.FileUtils;
import com.google.common.collect.Lists;
import com.swms.common.utils.utils.JsonUtils;
import com.swms.mdm.api.main.data.IOwnerMainDataApi;
import com.swms.mdm.api.main.data.ISkuMainDataApi;
import com.swms.mdm.api.main.data.IWarehouseMainDataApi;
import com.swms.mdm.api.main.data.dto.OwnerMainDataDTO;
import com.swms.mdm.api.main.data.dto.SkuMainDataDTO;
import com.swms.mdm.api.main.data.dto.WarehouseMainDataDTO;
import org.assertj.core.util.Sets;
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
import java.util.Set;

@SpringBootApplication(scanBasePackages = {"com.swms"})
@EnableDiscoveryClient
public class WmsApplicationTest {

    public static void main(String[] args) {
        SpringApplication.run(WmsApplicationTest.class, args);
    }

    @Bean("mockISkuMainDataApi")
    public ISkuMainDataApi iSkuMainDataApi() throws IOException {
        File file = ResourceUtils.getFile("classpath:json/SkuMainData.json");
        String s = FileUtils.readFileToString(file, "UTF-8");
        SkuMainDataDTO skuMainDataDTO = JsonUtils.string2Object(s, SkuMainDataDTO.class);

        ISkuMainDataApi iSkuMainDataApi = PowerMockito.mock(ISkuMainDataApi.class);
        PowerMockito.when(iSkuMainDataApi.getSkuMainData(BaseTest.SKU_CODE, BaseTest.OWNER_CODE))
            .thenAnswer(t -> skuMainDataDTO);

        Set<String> skuCodes = Sets.newHashSet();
        skuCodes.add(BaseTest.SKU_CODE);
        PowerMockito.when(iSkuMainDataApi.getSkuMainData(skuCodes)).thenAnswer(t -> Lists.newArrayList(skuMainDataDTO));

        return iSkuMainDataApi;
    }

    @Bean("mockIWarehouseMainDataApi")
    public IWarehouseMainDataApi iWarehouseMainDataApi() throws IOException {
        File file = ResourceUtils.getFile("classpath:json/WarehouseMainData.json");
        String s = FileUtils.readFileToString(file, "UTF-8");
        WarehouseMainDataDTO warehouseMainDataDTO = JsonUtils.string2Object(s, WarehouseMainDataDTO.class);

        IWarehouseMainDataApi iWarehouseMainDataApi = PowerMockito.mock(IWarehouseMainDataApi.class);
        PowerMockito.when(iWarehouseMainDataApi.getWarehouse(BaseTest.WAREHOUSE_CODE)).thenAnswer(t -> warehouseMainDataDTO);

        return iWarehouseMainDataApi;
    }

    @Bean("mockIOwnerMainDataApi")
    public IOwnerMainDataApi iOwnerMainDataApi() throws IOException {
        File file = ResourceUtils.getFile("classpath:json/OwnerMainData.json");
        String s = FileUtils.readFileToString(file, "UTF-8");
        OwnerMainDataDTO ownerMainDataDTO = JsonUtils.string2Object(s, OwnerMainDataDTO.class);

        IOwnerMainDataApi iOwnerMainDataApi = PowerMockito.mock(IOwnerMainDataApi.class);
        PowerMockito.when(iOwnerMainDataApi.getOwner(BaseTest.OWNER_CODE)).thenAnswer(t -> ownerMainDataDTO);

        return iOwnerMainDataApi;
    }

    @Test
    void test() {
        Assertions.assertTrue(true);
    }
}
