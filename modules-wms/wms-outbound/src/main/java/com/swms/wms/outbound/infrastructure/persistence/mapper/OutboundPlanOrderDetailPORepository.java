package com.swms.wms.outbound.infrastructure.persistence.mapper;

import com.swms.wms.outbound.infrastructure.persistence.po.OutboundPlanOrderDetailPO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutboundPlanOrderDetailPORepository extends JpaRepository<OutboundPlanOrderDetailPO, Long> {

    List<OutboundPlanOrderDetailPO> findAllByOutboundPlanOrderId(Long outboundPlanOrderId);
}
