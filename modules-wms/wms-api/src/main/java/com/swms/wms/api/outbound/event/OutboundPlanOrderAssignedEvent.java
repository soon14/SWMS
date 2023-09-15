package com.swms.wms.api.outbound.event;

import com.swms.domain.event.DomainEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class OutboundPlanOrderAssignedEvent extends DomainEvent {

    private Long outboundPlanOrderId;

    private String warehouseCode;
}
