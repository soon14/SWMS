package com.swms.inbound.infrastructure.persistence.mapper;

import com.swms.inbound.domain.entity.InboundPlanOrder;
import com.swms.inbound.infrastructure.persistence.po.InboundPlanOrderDetailPO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InboundPlanOrderDetailPORepository extends JpaRepository<InboundPlanOrderDetailPO, Long> {
}
