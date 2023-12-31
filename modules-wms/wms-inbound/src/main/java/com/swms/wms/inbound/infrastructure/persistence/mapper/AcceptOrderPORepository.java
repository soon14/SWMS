package com.swms.wms.inbound.infrastructure.persistence.mapper;

import com.swms.wms.inbound.infrastructure.persistence.po.AcceptOrderPO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AcceptOrderPORepository extends JpaRepository<AcceptOrderPO, Long> {

    List<AcceptOrderPO> findByInboundPlanOrderId(Long inboundPlanOrderId);
}
