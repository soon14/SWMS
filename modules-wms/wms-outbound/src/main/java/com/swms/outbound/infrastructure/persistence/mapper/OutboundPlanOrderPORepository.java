package com.swms.outbound.infrastructure.persistence.mapper;

import com.swms.outbound.infrastructure.persistence.po.OutboundPlanOrderPO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboundPlanOrderPORepository extends JpaRepository<OutboundPlanOrderPO, Long> {
    OutboundPlanOrderPO findByOrderNo(String orderNo);
}
