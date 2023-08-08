package com.swms.wms.inbound;

import com.swms.common.utils.utils.ObjectUtils;
import com.swms.wms.BaseTest;
import com.swms.wms.api.inbound.IReceiveOrderApi;
import com.swms.wms.api.inbound.dto.ReceiveOrderDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ReceiveOrderApiTest extends BaseTest {

    @Autowired
    private IReceiveOrderApi receiveOrderApi;

    @Test
    public void testCreateReceiveOrder() {

        ReceiveOrderDTO receiveOrderDTO = ObjectUtils.getRandomObjectIgnoreFields(ReceiveOrderDTO.class, "id", "version", "batchAttributes");
        receiveOrderApi.createReceiveOrder(receiveOrderDTO);

    }
}
