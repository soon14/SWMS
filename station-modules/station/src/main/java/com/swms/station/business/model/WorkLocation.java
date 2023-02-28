package com.swms.station.business.model;

import com.swms.common.constants.WorkLocationType;
import lombok.Data;

import java.util.List;

/**
 * definition: a place that robots working
 */
@Data
public class WorkLocation {

    /**
     * like SHELF, ROBOT, CONVEYOR and so on
     */
    private WorkLocationType workLocationType;
    private String workLocationCode;
    private List<Layout> layouts;

    private boolean enable;

    @Data
    public static class Layout {
        private String layoutCode;
        private List<Slot> slots;
    }
}
