package com.swms.inbound.domain.repository;

import com.swms.inbound.domain.entity.InboundPlanOrderDetail;

import java.util.List;

public interface InboundPlanOrderDetailRepository {
    void save(InboundPlanOrderDetail inboundPlanOrderDetail);

    void saveAll(List<InboundPlanOrderDetail> inboundPlanOrderDetails);

}
