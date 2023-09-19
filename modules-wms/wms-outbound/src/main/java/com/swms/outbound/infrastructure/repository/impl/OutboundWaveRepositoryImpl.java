package com.swms.outbound.infrastructure.repository.impl;

import com.swms.outbound.domain.entity.OutboundWave;
import com.swms.outbound.domain.repository.OutboundWaveRepository;
import com.swms.outbound.infrastructure.persistence.mapper.OutboundWavePORepository;
import com.swms.outbound.infrastructure.persistence.po.OutboundWavePO;
import com.swms.outbound.infrastructure.persistence.transfer.OutboundWavePOTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OutboundWaveRepositoryImpl implements OutboundWaveRepository {

    @Autowired
    private OutboundWavePORepository outboundWavePORepository;

    @Autowired
    private OutboundWavePOTransfer outboundWavePOTransfer;

    @Override
    public void save(OutboundWave outboundWave) {
        outboundWavePORepository.save(outboundWavePOTransfer.toPO(outboundWave));
    }

    @Override
    public OutboundWave findByWaveNo(String waveNo) {
        OutboundWavePO outboundWavePO = outboundWavePORepository.findByWaveNo(waveNo);
        return outboundWavePOTransfer.toDO(outboundWavePO);
    }
}
