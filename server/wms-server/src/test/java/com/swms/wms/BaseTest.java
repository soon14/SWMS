package com.swms.wms;

import com.swms.common.utils.user.UserContext;
import com.swms.common.utils.utils.ObjectUtils;
import com.swms.common.utils.utils.TenantContext;
import com.swms.wms.inbound.domain.service.impl.AcceptOrderServiceImpl;
import com.swms.wms.inbound.domain.service.impl.InboundPlanOrderServiceImpl;
import com.swms.mdm.api.config.IBatchAttributeConfigApi;
import com.swms.mdm.api.config.IParameterConfigApi;
import com.swms.mdm.api.main.data.IOwnerMainDataApi;
import com.swms.mdm.api.main.data.ISkuMainDataApi;
import com.swms.mdm.api.main.data.IWarehouseMainDataApi;
import com.swms.wms.outbound.infrastructure.remote.BatchAttributeConfigFacade;
import com.swms.wms.outbound.infrastructure.remote.OwnerMainDataFacade;
import com.swms.wms.outbound.infrastructure.remote.SkuMainDataFacade;
import com.swms.wms.outbound.infrastructure.remote.WarehouseMainDataFacade;
import com.swms.wms.api.basic.IPutWallApi;
import com.swms.wms.api.basic.IWorkStationApi;
import com.swms.wms.api.basic.dto.PutWallDTO;
import com.swms.wms.api.basic.dto.WorkStationDTO;
import com.swms.wms.api.inbound.IInboundPlanOrderApi;
import com.swms.wms.api.inbound.dto.InboundPlanOrderDTO;
import com.swms.wms.api.outbound.IOutboundPlanOrderApi;
import com.swms.wms.api.outbound.dto.OutboundPlanOrderDTO;
import com.swms.wms.api.stock.dto.StockCreateDTO;
import com.swms.wms.api.task.event.StockCreateEvent;
import com.swms.wms.stock.application.event.StockEventSubscriber;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.Collections;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = WmsApplicationTest.class)
public class BaseTest {

    @Autowired
    private StockEventSubscriber stockEventSubscriber;

    @Autowired
    private IInboundPlanOrderApi inboundPlanOrderApi;
    @Autowired
    private IOutboundPlanOrderApi outboundPlanOrderApi;
    @Autowired
    private IWorkStationApi workStationApi;
    @Autowired
    private IPutWallApi putWallApi;

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

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeEach
    public void initApiBean() {
        AcceptOrderServiceImpl acceptOrderService = applicationContext.getBean(AcceptOrderServiceImpl.class);
        InboundPlanOrderServiceImpl inboundPlanOrderService = applicationContext.getBean(InboundPlanOrderServiceImpl.class);
        ISkuMainDataApi iSkuMainDataApi = applicationContext.getBean("mockISkuMainDataApi", ISkuMainDataApi.class);
        IWarehouseMainDataApi iWarehouseMainDataApi = applicationContext.getBean("mockIWarehouseMainDataApi", IWarehouseMainDataApi.class);
        IOwnerMainDataApi iOwnerMainDataApi = applicationContext.getBean("mockIOwnerMainDataApi", IOwnerMainDataApi.class);
        IParameterConfigApi iParameterConfigApi = applicationContext.getBean("mockIParameterConfigApi", IParameterConfigApi.class);
        IBatchAttributeConfigApi iBatchAttributeConfigApi = applicationContext.getBean("mockIBatchAttributeConfigApi", IBatchAttributeConfigApi.class);

        acceptOrderService.setParameterConfigApi(iParameterConfigApi);
        inboundPlanOrderService.setIOwnerApi(iOwnerMainDataApi);
        inboundPlanOrderService.setISkuApi(iSkuMainDataApi);
        inboundPlanOrderService.setIWarehouseApi(iWarehouseMainDataApi);


        SkuMainDataFacade skuMainDataApi = applicationContext.getBean(SkuMainDataFacade.class);
        skuMainDataApi.setISkuMainDataApi(iSkuMainDataApi);
        WarehouseMainDataFacade warehouseMainDataApi = applicationContext.getBean(WarehouseMainDataFacade.class);
        warehouseMainDataApi.setIWarehouseMainDataApi(iWarehouseMainDataApi);
        OwnerMainDataFacade ownerMainDataApi = applicationContext.getBean(OwnerMainDataFacade.class);
        ownerMainDataApi.setIOwnerMainDataApi(iOwnerMainDataApi);
        BatchAttributeConfigFacade batchAttributeConfigApi = applicationContext.getBean(BatchAttributeConfigFacade.class);
        batchAttributeConfigApi.setSkuBatchAttributeConfigApi(iBatchAttributeConfigApi);
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

        inboundPlanOrderApi.createInboundPlanOrder(Collections.singletonList(inboundPlanOrderDTO));
    }

    protected void createOutboundPlanOrder() {

        OutboundPlanOrderDTO outboundPlanOrderDTO = ObjectUtils.
            getRandomObjectIgnoreFields(OutboundPlanOrderDTO.class, "id", "version", "batchAttributes", "qtyRequired", "qtyActual");
        outboundPlanOrderDTO.setCustomerOrderNo(CUSTOMER_ORDER_NO);
        outboundPlanOrderDTO.setWarehouseCode(WAREHOUSE_CODE);
        outboundPlanOrderDTO.setOwnerCode(OWNER_CODE);
        outboundPlanOrderDTO.getDetails().forEach(v -> {
            v.setQtyRequired(QTY_RESTORE);
            v.setSkuCode(SKU_CODE);
        });

        outboundPlanOrderApi.createOutboundPlanOrder(outboundPlanOrderDTO);
    }

    protected void createWorkStation() {
        WorkStationDTO workStationDTO = ObjectUtils.getRandomObjectIgnoreFields(WorkStationDTO.class, "id", "version", "workLocations");
        workStationDTO.setWarehouseCode(WAREHOUSE_CODE);
        workStationApi.save(workStationDTO);

        List<WorkStationDTO> workStationDTOS = workStationApi.getByWarehouseCode(WAREHOUSE_CODE);

        PutWallDTO putWallDTO = ObjectUtils.getRandomObjectIgnoreFields(PutWallDTO.class, "id", "version");
        putWallDTO.setWorkStationId(workStationDTOS.iterator().next().getId());
        putWallApi.save(putWallDTO);
    }

    @Test
    void test() {
        Assertions.assertTrue(true);
    }

}
