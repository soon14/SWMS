package com.swms.wms.outbound;

import com.swms.wms.outbound.application.event.OutboundPlanOrderSubscribe;
import com.swms.wms.outbound.application.event.OutboundWaveSubscribe;
import com.swms.wms.outbound.application.event.PickingOrderHandlerSubscribe;
import com.swms.wms.outbound.application.event.PickingOrderSubscribe;
import com.swms.wms.outbound.domain.entity.OutboundPlanOrder;
import com.swms.wms.outbound.domain.repository.OutboundPlanOrderRepository;
import com.swms.wms.BaseTest;
import com.swms.wms.api.outbound.event.NewOutboundPlanOrderEvent;
import com.swms.wms.api.outbound.event.NewPickingOrdersEvent;
import com.swms.wms.api.outbound.event.OrderPickingEvent;
import com.swms.wms.api.outbound.event.OutboundPlanOrderAssignedEvent;
import com.swms.wms.api.task.constants.OperationTaskTypeEnum;
import com.swms.wms.api.task.dto.OperationTaskDTO;
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

    @Autowired
    private PickingOrderSubscribe pickingOrderSubscribe;

    @Test
    void testCreateOutboundPlanOrder() {
        createOutboundPlanOrder();
        List<OutboundPlanOrder> outboundPlanOrders = outboundPlanOrderRepository.findByCustomerOrderNo(BaseTest.CUSTOMER_ORDER_NO);
        Assertions.assertTrue(CollectionUtils.isNotEmpty(outboundPlanOrders));
        outboundPlanOrderSubscribe.onCreateEvent(new NewOutboundPlanOrderEvent(outboundPlanOrders.iterator().next().getOrderNo()));
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

    @Test
    void testPickingEvent() {
        OperationTaskDTO operationTaskDTO = new OperationTaskDTO()
            .setId(492351075085586432L)
            .setTaskType(OperationTaskTypeEnum.PICKING)
            .setOperatedQty(3)
            .setOrderId(492297680446427136L)
            .setDetailId(492297680685502464L);
//        pickingOrderSubscribe.onPickingEvent(new OperationTaskFinishedEvent().setOperationTasks(Lists.newArrayList(operationTaskDTO)));

        OrderPickingEvent.PickingDetail pickingDetail = new OrderPickingEvent.PickingDetail().setOutboundOrderId(492015224904355840L).setOutboundOrderDetailId(492015233691422720L)
            .setOperatedQty(3);
        outboundPlanOrderSubscribe.onPickingEvent(new OrderPickingEvent().setPickingDetails(Lists.newArrayList(pickingDetail)));
    }
}
