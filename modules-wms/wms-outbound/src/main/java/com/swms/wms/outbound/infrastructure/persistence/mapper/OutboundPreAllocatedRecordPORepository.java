package com.swms.wms.outbound.infrastructure.persistence.mapper;

import com.swms.wms.outbound.infrastructure.persistence.po.OutboundPreAllocatedRecordPO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutboundPreAllocatedRecordPORepository extends JpaRepository<OutboundPreAllocatedRecordPO, Long> {

    List<OutboundPreAllocatedRecordPO> findByOutboundPlanOrderId(Long outboundPlanOrderId);
}
