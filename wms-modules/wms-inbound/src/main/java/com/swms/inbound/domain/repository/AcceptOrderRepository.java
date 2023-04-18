package com.swms.inbound.domain.repository;

import com.swms.inbound.domain.entity.AcceptOrder;


public interface AcceptOrderRepository {

    void save(AcceptOrder acceptOrder);

    AcceptOrder findById(Long acceptOrderId);

}
