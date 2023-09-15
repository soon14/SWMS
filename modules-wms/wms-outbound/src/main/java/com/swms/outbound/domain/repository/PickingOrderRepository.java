package com.swms.outbound.domain.repository;

import com.swms.outbound.domain.entity.PickingOrder;

import java.util.List;

public interface PickingOrderRepository {
    void saveAll(List<PickingOrder> pickingOrders);

    List<PickingOrder> findByPickingOrderNos(List<String> pickingOrderNos);
}
