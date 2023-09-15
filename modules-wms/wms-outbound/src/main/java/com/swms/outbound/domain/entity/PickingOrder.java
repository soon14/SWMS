package com.swms.outbound.domain.entity;

import com.swms.wms.api.outbound.constants.PickingOrderStatusEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class PickingOrder {

    private String waveNo;

    private String pickingOrderNo;

    private PickingOrderStatusEnum pickingOrderStatus;

    private List<PickingOrderDetail> details;
}
