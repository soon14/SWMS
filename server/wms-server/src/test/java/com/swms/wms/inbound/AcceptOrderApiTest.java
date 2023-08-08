package com.swms.wms.inbound;

import com.swms.inbound.domain.entity.AcceptOrder;
import com.swms.inbound.domain.entity.InboundPlanOrder;
import com.swms.inbound.domain.repository.AcceptOrderRepository;
import com.swms.inbound.domain.repository.InboundPlanOrderRepository;
import com.swms.wms.BaseTest;
import com.swms.wms.api.inbound.IAcceptOrderApi;
import com.swms.wms.api.inbound.constants.AcceptMethodEnum;
import com.swms.wms.api.inbound.constants.AcceptTypeEnum;
import com.swms.wms.api.inbound.dto.AcceptRecordDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

class AcceptOrderApiTest extends BaseTest {

    @Autowired
    private IAcceptOrderApi acceptOrderApi;

    @Autowired
    private AcceptOrderRepository acceptOrderRepository;

    @Autowired
    private InboundPlanOrderRepository inboundPlanOrderRepository;

    @Test
    @Transactional
    void testAcceptOrder() {

        createInboundPlanOrder();

        AcceptRecordDTO acceptRecord = new AcceptRecordDTO();
        acceptRecord.setQtyAccepted(3);
        acceptRecord.setBoxNo(BOX_NO);
        acceptRecord.setSkuCode(SKU_CODE);
        acceptRecord.setTargetContainerCode("123");
        acceptRecord.setTargetContainerSpecCode("234");
        acceptRecord.setTargetContainerSlotCode("A");
        acceptRecord.setStationId(1L);
        acceptRecord.setWarehouseCode(WAREHOUSE_CODE);
        acceptRecord.setAcceptType(AcceptTypeEnum.DIRECT);
        acceptRecord.setAcceptMethod(AcceptMethodEnum.BOX_CONTENT);

        acceptOrderApi.accept(acceptRecord);

        List<InboundPlanOrder> inboundPlanOrders = inboundPlanOrderRepository.findByCustomerOrderNo(CUSTOMER_ORDER_NO);
        List<AcceptOrder> acceptOrders = acceptOrderRepository.findByInboundPlanOrderId(inboundPlanOrders.get(0).getId());

        Assertions.assertTrue(CollectionUtils.isNotEmpty(acceptOrders));
    }

    @Test
    void testAudit() throws InterruptedException {
        testAcceptOrder();
        InboundPlanOrder inboundPlanOrder = inboundPlanOrderRepository.findByCustomerOrderNo(CUSTOMER_ORDER_NO)
            .get(0);
        Integer oldQtyAccepted = inboundPlanOrderRepository.findById(inboundPlanOrder.getId())
            .getInboundPlanOrderDetails().get(0).getQtyAccepted();

        List<AcceptOrder> acceptOrders = acceptOrderRepository.findByInboundPlanOrderId(inboundPlanOrder.getId());
        AcceptOrder acceptOrder = acceptOrders.get(0);
        acceptOrderApi.audit(acceptOrder.getId());

        Integer qtyAccepted = acceptOrder.getAcceptOrderDetails().get(0).getQtyAccepted();

        Thread.sleep(1000L);
        Integer newQtyAccepted = inboundPlanOrderRepository.findById(inboundPlanOrder.getId())
            .getInboundPlanOrderDetails().get(0).getQtyAccepted();

        Assertions.assertEquals(qtyAccepted + oldQtyAccepted, (int) newQtyAccepted);
    }

}
