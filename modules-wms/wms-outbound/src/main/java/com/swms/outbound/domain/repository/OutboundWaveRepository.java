package com.swms.outbound.domain.repository;

import com.swms.outbound.domain.entity.OutboundWave;

public interface OutboundWaveRepository {
    void save(OutboundWave outboundWave);

    OutboundWave findByWaveNo(String waveNo);
}
