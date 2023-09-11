package com.swms.wms.api.outbound.event;

import com.swms.domain.event.DomainEvent;
import com.swms.wms.api.outbound.dto.OutboundPlanOrderDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class NewOutboundWaveEvent extends DomainEvent {

    private String waveNo;

}
