package com.swms.outbound.infrastructure.persistence.mapper;

import com.swms.outbound.infrastructure.persistence.po.OutboundPlanOrderDetailPO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboundPlanOrderDetailPORepository extends JpaRepository<OutboundPlanOrderDetailPO, Long> {
}
