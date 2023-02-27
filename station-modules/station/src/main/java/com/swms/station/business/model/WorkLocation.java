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
    private String robotCode;

    private List<Layout> layouts;

    private boolean enable;

    @Data
    private static class Layout {
        private Integer level;
        private Integer bay;
        private String code;
        private String containerCode;
        private String containerType;
        private String containerFace;
    }
}
