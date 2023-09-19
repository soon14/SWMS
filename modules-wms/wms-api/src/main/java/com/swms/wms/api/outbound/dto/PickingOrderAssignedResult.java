package com.swms.wms.api.outbound.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

@Data
@Accessors(chain = true)
public class PickingOrderAssignedResult {

    private String pickingOrderNo;

    private Map<Long, String> assignedStationSlot;
}
