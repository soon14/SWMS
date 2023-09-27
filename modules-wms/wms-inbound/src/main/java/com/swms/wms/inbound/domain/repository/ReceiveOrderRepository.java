package com.swms.wms.inbound.domain.repository;

import com.swms.wms.inbound.domain.entity.ReceiveOrder;

public interface ReceiveOrderRepository {

    void saveOrderAndDetail(ReceiveOrder receiveOrder);

    ReceiveOrder findById(Long receiveOrderId);

}
