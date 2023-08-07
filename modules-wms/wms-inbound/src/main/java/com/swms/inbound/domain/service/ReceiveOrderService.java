package com.swms.inbound.domain.service;

import com.swms.inbound.domain.entity.ReceiveOrder;

public interface ReceiveOrderService {
    void validateReceiveOrder(ReceiveOrder receiveOrder);
}
