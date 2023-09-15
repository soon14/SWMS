package com.swms.outbound.domain.entity;

import com.swms.wms.api.outbound.constants.OutboundWaveStatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class OutboundWave {

    private String warehouseCode;
    private String waveNo;
    private List<OutboundPlanOrder> orders;

    public OutboundWave(String waveNo, List<OutboundPlanOrder> orders) {
        this.waveNo = waveNo;
        this.orders = orders;
    }

    private OutboundWaveStatusEnum waveStatus;

    public void process() {
        this.waveStatus = OutboundWaveStatusEnum.PROCESSING;
    }
}
