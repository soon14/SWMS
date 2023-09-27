package com.swms.wms.outbound.domain.repository;

import com.swms.wms.outbound.domain.entity.OutboundPreAllocatedRecord;

import java.util.List;

public interface OutboundPreAllocatedRecordRepository {
    void saveAll(List<OutboundPreAllocatedRecord> planPreAllocatedRecords);

    List<OutboundPreAllocatedRecord> findByOutboundPlanOrderId(Long outboundPlanOrderId);
}
