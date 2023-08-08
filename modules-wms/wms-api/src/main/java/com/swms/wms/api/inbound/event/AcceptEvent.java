package com.swms.wms.api.inbound.event;

import com.swms.domain.event.DomainEvent;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class AcceptEvent extends DomainEvent {

    private Long inboundPlanOrderId;

    private List<AcceptDetail> acceptDetails;

    @Data
    @Builder
    public static class AcceptDetail {

        private Long inboundPlanOrderDetailId;
        private Integer qtyAccepted;
    }
}
