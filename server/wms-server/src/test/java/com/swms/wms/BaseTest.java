package com.swms.wms;

import com.swms.common.utils.user.UserContext;
import com.swms.common.utils.utils.JsonUtils;
import com.swms.common.utils.utils.ObjectUtils;
import com.swms.inbound.domain.service.impl.AcceptOrderServiceImpl;
import com.swms.inbound.domain.service.impl.InboundPlanOrderServiceImpl;
import com.swms.mdm.api.config.IParameterConfigApi;
import com.swms.mdm.api.config.dto.ParameterConfigDTO;
import com.swms.mdm.api.main.data.IOwnerMainDataApi;
import com.swms.mdm.api.main.data.ISkuMainDataApi;
import com.swms.mdm.api.main.data.IWarehouseMainDataApi;
import com.swms.tenant.config.util.TenantContext;
import com.swms.wms.api.inbound.IInboundPlanOrderApi;
import com.swms.wms.api.inbound.dto.InboundPlanOrderDTO;
import com.swms.wms.api.stock.dto.StockCreateDTO;
import com.swms.wms.api.task.event.StockCreateEvent;
import com.swms.wms.stock.application.event.StockEventSubscriber;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = WmsApplicationTest.class)
public class BaseTest {

    @Autowired
    private StockEventSubscriber stockEventSubscriber;

    @Autowired
    private IInboundPlanOrderApi inboundPlanOrderApi;

    @Autowired
    protected ApplicationContext applicationContext;

    public static final String WAREHOUSE_CODE = "WAREHOUSE_CODE";
    public static final String OWNER_CODE = "OWNER_CODE";
    public static final String INBOUND_ORDER_TYPE = "INBOUND_ORDER_TYPE";
    protected final String containerCode = "A00000001";
    protected final String containerSlotCode = "A";
    protected static final String CUSTOMER_ORDER_NO = "CUSTOMER_ORDER_NO";
    protected static final String BOX_NO = "BOX_NO";
    protected static final int QTY_RESTORE = 10;
    protected static final String SKU_CODE = "SKU_CODE";

    @BeforeEach
    public void initApiBean() {
        AcceptOrderServiceImpl acceptOrderService = applicationContext.getBean(AcceptOrderServiceImpl.class);
        InboundPlanOrderServiceImpl inboundPlanOrderService = applicationContext.getBean(InboundPlanOrderServiceImpl.class);
        ISkuMainDataApi iSkuMainDataApi = applicationContext.getBean("mockISkuMainDataApi", ISkuMainDataApi.class);
        IWarehouseMainDataApi iWarehouseMainDataApi = applicationContext.getBean("mockIWarehouseMainDataApi", IWarehouseMainDataApi.class);
        IOwnerMainDataApi iOwnerMainDataApi = applicationContext.getBean("mockIOwnerMainDataApi", IOwnerMainDataApi.class);
        IParameterConfigApi iParameterConfigApi = applicationContext.getBean("mockIParameterConfigApi", IParameterConfigApi.class);

        acceptOrderService.setParameterConfigApi(iParameterConfigApi);
        inboundPlanOrderService.setIOwnerApi(iOwnerMainDataApi);
        inboundPlanOrderService.setISkuApi(iSkuMainDataApi);
        inboundPlanOrderService.setIWarehouseApi(iWarehouseMainDataApi);
    }

    @BeforeAll
    protected static void initBean() {
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

    protected void createInboundPlanOrder() {

        InboundPlanOrderDTO inboundPlanOrderDTO = ObjectUtils.
            getRandomObjectIgnoreFields(InboundPlanOrderDTO.class, "id", "version", "batchAttributes", "qtyAccepted", "qtyAbnormal", "qtyUnreceived");
        inboundPlanOrderDTO.setCustomerOrderNo(CUSTOMER_ORDER_NO);
        inboundPlanOrderDTO.setWarehouseCode(WAREHOUSE_CODE);
        inboundPlanOrderDTO.setOwnerCode(OWNER_CODE);
        inboundPlanOrderDTO.getInboundPlanOrderDetails().forEach(v -> {
            v.setBoxNo(BOX_NO);
            v.setQtyRestocked(QTY_RESTORE);
            v.setSkuCode(SKU_CODE);
        });

        inboundPlanOrderApi.createInboundPlanOrder(inboundPlanOrderDTO);
    }

    @Test
    void test() {
        Assertions.assertTrue(true);
    }

}
