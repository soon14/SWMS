package com.swms.outbound.domain.entity;

import lombok.Data;

import java.util.List;

@Data
public class OutboundWave {

    private String waveNo;
    private List<OutboundPlanOrder> orders;
}
