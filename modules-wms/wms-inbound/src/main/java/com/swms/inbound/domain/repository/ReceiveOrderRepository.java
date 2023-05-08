package com.swms.inbound.domain.repository;

import com.swms.inbound.domain.entity.ReceiveOrder;


public interface ReceiveOrderRepository {

    void save(ReceiveOrder receiveOrder);

    ReceiveOrder findById(Long receiveOrderId);

}
