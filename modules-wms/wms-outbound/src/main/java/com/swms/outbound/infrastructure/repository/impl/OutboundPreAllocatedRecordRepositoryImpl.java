package com.swms.outbound.infrastructure.repository.impl;

import com.swms.outbound.domain.entity.OutboundPreAllocatedRecord;
import com.swms.outbound.domain.repository.OutboundPreAllocatedRecordRepository;
import com.swms.outbound.infrastructure.persistence.mapper.OutboundPreAllocatedRecordPORepository;
import com.swms.outbound.infrastructure.persistence.po.OutboundPreAllocatedRecordPO;
import com.swms.outbound.infrastructure.persistence.transfer.OutboundPreAllocatedRecordPOTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OutboundPreAllocatedRecordRepositoryImpl implements OutboundPreAllocatedRecordRepository {

    @Autowired
    private OutboundPreAllocatedRecordPORepository outboundPreAllocatedRecordPORepository;

    @Autowired
    private OutboundPreAllocatedRecordPOTransfer outboundPreAllocatedRecordPOTransfer;

    @Override
    public void saveAll(List<OutboundPreAllocatedRecord> planPreAllocatedRecords) {
        outboundPreAllocatedRecordPORepository.saveAll(outboundPreAllocatedRecordPOTransfer.toPOs(planPreAllocatedRecords));
    }

    @Override
    public List<OutboundPreAllocatedRecord> findByOutboundPlanOrderId(Long outboundPlanOrderId) {
        List<OutboundPreAllocatedRecordPO> recordPOS = outboundPreAllocatedRecordPORepository.findByOutboundPlanOrderId(outboundPlanOrderId);
        return outboundPreAllocatedRecordPOTransfer.toDOs(recordPOS);
    }
}
