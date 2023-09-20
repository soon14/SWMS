package com.swms.wms.outbound;

import com.swms.outbound.application.event.OutboundPlanOrderSubscribe;
import com.swms.outbound.application.event.OutboundWaveSubscribe;
import com.swms.outbound.application.event.PickingOrderHandlerSubscribe;
import com.swms.outbound.domain.entity.OutboundPlanOrder;
import com.swms.outbound.domain.repository.OutboundPlanOrderRepository;
import com.swms.wms.BaseTest;
import com.swms.wms.api.outbound.event.NewOutboundPlanOrderEvent;
import com.swms.wms.api.outbound.event.NewPickingOrdersEvent;
import com.swms.wms.api.outbound.event.OutboundPlanOrderAssignedEvent;
import org.apache.commons.collections4.CollectionUtils;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

class OutboundPlanOrderApiTest extends BaseTest {

    @Autowired
    private OutboundPlanOrderRepository outboundPlanOrderRepository;

    @Autowired
    private OutboundPlanOrderSubscribe outboundPlanOrderSubscribe;

    @Autowired
    private OutboundWaveSubscribe outboundWaveSubscribe;

    @Autowired
    private PickingOrderHandlerSubscribe pickingOrderHandlerSubscribe;

    @Test
    void testCreateOutboundPlanOrder() {
        createOutboundPlanOrder();
        List<OutboundPlanOrder> outboundPlanOrders = outboundPlanOrderRepository.findByCustomerOrderNo(BaseTest.CUSTOMER_ORDER_NO);
        Assertions.assertTrue(CollectionUtils.isNotEmpty(outboundPlanOrders));
        outboundPlanOrderSubscribe.onEvent(new NewOutboundPlanOrderEvent(outboundPlanOrders.iterator().next().getOrderNo()));
    }

    @Test
    void testWavePicking() {
        List<OutboundPlanOrder> outboundPlanOrders = outboundPlanOrderRepository.findByCustomerOrderNo(BaseTest.CUSTOMER_ORDER_NO);
        OutboundPlanOrder outboundPlanOrder = outboundPlanOrders.iterator().next();
        outboundWaveSubscribe.onEvent(new OutboundPlanOrderAssignedEvent()
            .setOutboundPlanOrderId(outboundPlanOrder.getId()).setWarehouseCode(outboundPlanOrder.getWarehouseCode()));
    }

    @Test
    void testPickingOrderHandling() {
        pickingOrderHandlerSubscribe.onEvent(new NewPickingOrdersEvent()
            .setPickingOrderNos(Lists.newArrayList("PICK_2023-09-20-000060")).setWarehouseCode(WAREHOUSE_CODE));
    }
}
