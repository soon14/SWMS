package com.swms.wms.warehouse.work_station.domain.entity;

import com.swms.wms.api.warehouse.constants.WorkLocationType;
import lombok.Data;

import java.util.List;

/**
 * definition: a place that robots working
 */
@Data
public class WorkLocation {

    private Long id;

    private String stationCode;
    /**
     * like SHELF, ROBOT, CONVEYOR and so on
     */
    private WorkLocationType workLocationType;
    private String workLocationCode;

    private boolean enable;

    private List<WorkLocationSlot> workLocationSlots;
}
