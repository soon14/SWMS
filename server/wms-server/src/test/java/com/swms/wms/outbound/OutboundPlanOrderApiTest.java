package com.swms.wms.outbound;

import com.swms.common.utils.utils.ObjectUtils;
import com.swms.wms.BaseTest;
import com.swms.wms.api.outbound.IOutboundPlanOrderApi;
import com.swms.wms.api.outbound.dto.OutboundPlanOrderDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class OutboundPlanOrderApiTest extends BaseTest {

    @Autowired
    private IOutboundPlanOrderApi outboundPlanOrderApi;

    @Test
    void testCreateOutboundPlanOrder() {
        createOutboundPlanOrder();
    }
}
