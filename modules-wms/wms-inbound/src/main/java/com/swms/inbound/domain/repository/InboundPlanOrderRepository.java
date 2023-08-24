package com.swms.inbound.domain.repository;

import com.swms.inbound.domain.entity.InboundPlanOrder;

import java.util.Collection;
import java.util.List;


public interface InboundPlanOrderRepository {

    void saveOrderAndDetail(InboundPlanOrder inboundPlanOrder);

    InboundPlanOrder findById(Long inboundPlanOrderId);

    List<InboundPlanOrder> findByCustomerOrderNo(String customerOrderNo);

    List<InboundPlanOrder> findInboundPlanOrderByCustomerOrderNos(Collection<String> customerOrderNo);

    InboundPlanOrder findByBoxNo(String boxNo, String warehouseCode);

    boolean existByBoxNos(Collection<String> boxNos, String warehouseCode);
}
