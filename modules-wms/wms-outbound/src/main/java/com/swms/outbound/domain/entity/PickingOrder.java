package com.swms.outbound.domain.entity;

import com.swms.wms.api.outbound.constants.PickingOrderStatusEnum;

import java.util.List;

public class PickingOrder {

    private String waveNo;

    private String pickingOrderNo;

    private PickingOrderStatusEnum pickingOrderStatus;

    private List<PickingOrderDetail> details;
}
