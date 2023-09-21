package com.swms.outbound.domain.repository;

import com.swms.outbound.domain.entity.PickingOrder;

import java.util.Collection;
import java.util.List;

public interface PickingOrderRepository {
    void saveAll(List<PickingOrder> pickingOrders);

    List<PickingOrder> findByPickingOrderNos(Collection<String> pickingOrderNos);

    void saveOrderAndDetails(List<PickingOrder> pickingOrders);

    List<PickingOrder> findByPickingOrderIds(Collection<Long> pickingOrderIds);

    List<PickingOrder> findByWaveNos(List<String> waveNos);
}
