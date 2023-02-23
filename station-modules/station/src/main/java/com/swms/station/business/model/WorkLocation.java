package com.swms.station.business.model;

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
    private String workLocationType;

    private String businessType;

    private String warehouseCode;
    private String warehouseLogicCode;
    private String warehouseAreaCode;

    private String workLocationCode;
    private String robotCode;

    private List<Layout> layouts;

    private String stationStatus;

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
