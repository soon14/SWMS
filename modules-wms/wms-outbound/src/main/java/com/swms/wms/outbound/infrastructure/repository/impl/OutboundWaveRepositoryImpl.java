package com.swms.wms.outbound.infrastructure.repository.impl;

import com.swms.wms.outbound.domain.entity.OutboundWave;
import com.swms.wms.outbound.domain.repository.OutboundWaveRepository;
import com.swms.wms.outbound.infrastructure.persistence.mapper.OutboundWavePORepository;
import com.swms.wms.outbound.infrastructure.persistence.po.OutboundWavePO;
import com.swms.wms.outbound.infrastructure.persistence.transfer.OutboundWavePOTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<OutboundWave> findByWaveNos(List<String> waveNos) {
        List<OutboundWavePO> outboundWavePOS = outboundWavePORepository.findByWaveNoIn(waveNos);
        return outboundWavePOTransfer.toDOs(outboundWavePOS);
    }

    @Override
    public void saveAll(List<OutboundWave> outboundWaves) {
        outboundWavePORepository.saveAll(outboundWavePOTransfer.toPOs(outboundWaves));
    }
}
