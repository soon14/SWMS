package com.swms.wms.api.outbound.event;

import com.swms.domain.event.DomainEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class OrderPickingEvent extends DomainEvent {

    private List<PickingDetail> pickingDetails;

    @Data
    @Accessors(chain = true)
    public static class PickingDetail {
        private Long outboundOrderDetailId;
        private Long outboundOrderId;
        private Integer operatedQty;
    }
}
