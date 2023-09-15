package com.swms.outbound.domain.repository;

import com.swms.outbound.domain.entity.OutboundOrderPlanPreAllocatedRecord;

import java.util.List;

public interface OutboundPreAllocatedRecordRepository {
    void saveAll(List<OutboundOrderPlanPreAllocatedRecord> planPreAllocatedRecords);

    List<OutboundOrderPlanPreAllocatedRecord> findByOutboundPlanOrderId(Long outboundPlanOrderId);
}
