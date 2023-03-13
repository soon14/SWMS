package com.swms.wms.api.warehouse.dto;

import com.swms.wms.api.warehouse.constants.WorkLocationType;
import lombok.Data;

import java.util.List;

@Data
public class WorkLocationDTO {

    private String stationCode;
    /**
     * like SHELF, ROBOT, CONVEYOR and so on
     */
    private WorkLocationType workLocationType;
    private String workLocationCode;

    private boolean enable;

    private List<WorkLocationSlotDTO> workLocationSlots;
}
