package com.swms.wms;

import com.alibaba.cloud.commons.io.FileUtils;
import com.google.common.collect.Lists;
import com.swms.common.utils.utils.JsonUtils;
import com.swms.mdm.api.config.IBatchAttributeConfigApi;
import com.swms.mdm.api.config.IParameterConfigApi;
import com.swms.mdm.api.config.constants.ParameterCodeEnum;
import com.swms.mdm.api.config.dto.ParameterConfigDTO;
import com.swms.mdm.api.main.data.IOwnerMainDataApi;
import com.swms.mdm.api.main.data.ISkuMainDataApi;
import com.swms.mdm.api.main.data.IWarehouseMainDataApi;
import com.swms.mdm.api.main.data.dto.OwnerMainDataDTO;
import com.swms.mdm.api.main.data.dto.SkuMainDataDTO;
import com.swms.mdm.api.main.data.dto.WarehouseMainDataDTO;
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

        PowerMockito.when(iSkuMainDataApi.getSkuMainData(Lists.newArrayList(BaseTest.SKU_CODE)))
            .thenAnswer(t -> Lists.newArrayList(skuMainDataDTO));

        return iSkuMainDataApi;
    }

    @Bean("mockIWarehouseMainDataApi")
    public IWarehouseMainDataApi iWarehouseMainDataApi() throws IOException {
        File file = ResourceUtils.getFile("classpath:json/WarehouseMainData.json");
        String s = FileUtils.readFileToString(file, "UTF-8");
        WarehouseMainDataDTO warehouseMainDataDTO = JsonUtils.string2Object(s, WarehouseMainDataDTO.class);

        IWarehouseMainDataApi iWarehouseMainDataApi = PowerMockito.mock(IWarehouseMainDataApi.class);
        PowerMockito.when(iWarehouseMainDataApi.getWarehouse(BaseTest.WAREHOUSE_CODE)).thenAnswer(t -> warehouseMainDataDTO);
        PowerMockito.when(iWarehouseMainDataApi.getWarehouses(Lists.newArrayList(BaseTest.WAREHOUSE_CODE)))
            .thenAnswer(t -> Lists.newArrayList(warehouseMainDataDTO));

        return iWarehouseMainDataApi;
    }

    @Bean("mockIOwnerMainDataApi")
    public IOwnerMainDataApi iOwnerMainDataApi() throws IOException {
        File file = ResourceUtils.getFile("classpath:json/OwnerMainData.json");
        String s = FileUtils.readFileToString(file, "UTF-8");
        OwnerMainDataDTO ownerMainDataDTO = JsonUtils.string2Object(s, OwnerMainDataDTO.class);

        IOwnerMainDataApi iOwnerMainDataApi = PowerMockito.mock(IOwnerMainDataApi.class);
        PowerMockito.when(iOwnerMainDataApi.getOwner(BaseTest.OWNER_CODE)).thenAnswer(t -> ownerMainDataDTO);
        PowerMockito.when(iOwnerMainDataApi.getOwners(Lists.newArrayList(BaseTest.OWNER_CODE)))
            .thenAnswer(t -> Lists.newArrayList(ownerMainDataDTO));

        return iOwnerMainDataApi;
    }

    @Bean("mockIParameterConfigApi")
    public IParameterConfigApi iParameterConfigApi() throws IOException {
        File file = ResourceUtils.getFile("classpath:json/ParameterConfig.json");
        String s = FileUtils.readFileToString(file, "UTF-8");
        ParameterConfigDTO parameterConfigDTO = JsonUtils.string2Object(s, ParameterConfigDTO.class);

        IParameterConfigApi iParameterConfigApi = PowerMockito.mock(IParameterConfigApi.class);
        PowerMockito.when(iParameterConfigApi
                .getBooleanParameter(ParameterCodeEnum.INBOUND_OVER_ACCEPT, BaseTest.OWNER_CODE, BaseTest.INBOUND_ORDER_TYPE))
            .thenAnswer(t -> parameterConfigDTO);
        PowerMockito.when(iParameterConfigApi
                .getBooleanParameter(ParameterCodeEnum.INBOUND_ALLOW_MULTIPLE_ARRIVALS, BaseTest.OWNER_CODE, BaseTest.INBOUND_ORDER_TYPE))
            .thenAnswer(t -> parameterConfigDTO);

        return iParameterConfigApi;
    }

    @Bean("mockIBatchAttributeConfigApi")
    public IBatchAttributeConfigApi iBatchAttributeConfigApi() {
        IBatchAttributeConfigApi iBatchAttributeConfigApi = PowerMockito.mock(IBatchAttributeConfigApi.class);
        PowerMockito.when(iBatchAttributeConfigApi.getByOwnerAndSkuFirstCategory(BaseTest.OWNER_CODE, ""))
            .thenAnswer(t -> null);
        return iBatchAttributeConfigApi;
    }

    @Test
    void test() {
        Assertions.assertTrue(true);
    }
}
