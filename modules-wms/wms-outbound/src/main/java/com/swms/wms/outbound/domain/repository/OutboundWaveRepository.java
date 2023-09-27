package com.swms.wms.outbound.domain.repository;

import com.swms.wms.outbound.domain.entity.OutboundWave;

import java.util.List;

public interface OutboundWaveRepository {

    void save(OutboundWave outboundWave);

    OutboundWave findByWaveNo(String waveNo);

    List<OutboundWave> findByWaveNos(List<String> waveNos);

    void saveAll(List<OutboundWave> outboundWaves);
}
