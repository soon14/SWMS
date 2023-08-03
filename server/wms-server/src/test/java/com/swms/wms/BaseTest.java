package com.swms.wms;

import com.swms.common.utils.user.UserContext;
import com.swms.tenant.config.util.TenantContext;
import com.swms.wms.api.stock.dto.StockCreateDTO;
import com.swms.wms.api.task.event.StockCreateEvent;
import com.swms.wms.stock.application.event.StockEventSubscriber;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = WmsApplicationTest.class)
public class BaseTest {

    @Autowired
    private StockEventSubscriber stockEventSubscriber;

    protected final String containerCode = "A00000001";
    protected final String containerSlotCode = "A";

    @BeforeAll
    static void initBean() {
        //set default tenant
        TenantContext.setCurrentTenant("test");

        //set default user
        UserContext.setUserName("linsan");
    }

    protected void createContainerStock() {
        StockCreateDTO stockTransferDTO = new StockCreateDTO();
        stockTransferDTO.setTargetContainerCode(containerCode);
        stockTransferDTO.setTargetContainerSlotCode(containerSlotCode);
        stockTransferDTO.setSourceContainerCode("lpn");
        stockTransferDTO.setTransferQty(10);
        stockTransferDTO.setSkuBatchAttributeId(1L);
        stockTransferDTO.setSkuId(1L);
        stockTransferDTO.setOrderNo("ABC_ORDER");
        stockTransferDTO.setWarehouseAreaId(1L);
        stockTransferDTO.setWarehouseCode("ABC");

        stockEventSubscriber.onEvent(StockCreateEvent.builder().stockCreateDTO(stockTransferDTO).build());
    }

    @Test
    void test() {
        Assertions.assertTrue(true);
    }
}
