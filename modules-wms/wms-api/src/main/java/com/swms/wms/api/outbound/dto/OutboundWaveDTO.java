package com.swms.wms.api.outbound.dto;

import lombok.Data;

import java.util.List;

@Data
public class OutboundWaveDTO {

    private String waveNo;
    private List<Long> outboundPlanOrderIds;
}
