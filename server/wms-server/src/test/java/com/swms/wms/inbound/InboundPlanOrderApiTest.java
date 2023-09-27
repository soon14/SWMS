package com.swms.wms.inbound;

import com.swms.wms.inbound.domain.entity.InboundPlanOrder;
import com.swms.wms.inbound.domain.repository.InboundPlanOrderRepository;
import com.swms.wms.BaseTest;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

class InboundPlanOrderApiTest extends BaseTest {

    @Autowired
    private InboundPlanOrderRepository inboundPlanOrderRepository;

    @Test
    @Transactional
    void testCreateInboundPlanOrder() {

        createInboundPlanOrder();
        List<InboundPlanOrder> inboundPlanOrders = inboundPlanOrderRepository.findByCustomerOrderNo(CUSTOMER_ORDER_NO);
        Assertions.assertTrue(CollectionUtils.isNotEmpty(inboundPlanOrders));
        Assertions.assertTrue(inboundPlanOrders.stream().anyMatch(v -> StringUtils.equals(CUSTOMER_ORDER_NO, v.getCustomerOrderNo())));
    }
}
