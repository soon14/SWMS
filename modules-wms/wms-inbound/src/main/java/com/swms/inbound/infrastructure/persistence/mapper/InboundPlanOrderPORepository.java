package com.swms.inbound.infrastructure.persistence.mapper;

import com.swms.inbound.infrastructure.persistence.po.InboundPlanOrderPO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InboundPlanOrderPORepository extends JpaRepository<InboundPlanOrderPO, Long> {
}
