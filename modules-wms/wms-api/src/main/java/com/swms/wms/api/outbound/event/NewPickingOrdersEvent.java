package com.swms.wms.api.outbound.event;

import com.swms.domain.event.DomainEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class NewPickingOrdersEvent extends DomainEvent {

    private List<String> pickingOrderNos;

    private String warehouseCode;
}
