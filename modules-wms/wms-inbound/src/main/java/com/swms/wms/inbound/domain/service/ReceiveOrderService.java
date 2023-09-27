package com.swms.wms.inbound.domain.service;

import com.swms.wms.inbound.domain.entity.ReceiveOrder;

public interface ReceiveOrderService {
    void validateReceiveOrder(ReceiveOrder receiveOrder);
}
