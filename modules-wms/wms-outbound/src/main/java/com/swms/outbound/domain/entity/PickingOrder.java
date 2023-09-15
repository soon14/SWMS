package com.swms.outbound.domain.entity;

import com.swms.wms.api.outbound.constants.PickingOrderStatusEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

@Data
@Accessors(chain = true)
public class PickingOrder {

    private String waveNo;

    private String pickingOrderNo;

    private PickingOrderStatusEnum pickingOrderStatus;

    private List<PickingOrderDetail> details;

    /**
     * one picking order can be assigned to multiple station slot
     * <p>
     * Key is the station id
     * Value is the put wall slot code
     */
    private Map<String, String> assignedStationSlot;
}
