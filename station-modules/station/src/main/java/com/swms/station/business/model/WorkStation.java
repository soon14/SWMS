package com.swms.station.business.model;

import lombok.Data;

import java.util.List;

/**
 * definition：a place that operators working
 */
@Data
public class WorkStation {

    private String stationCode;

    private List<WorkLocation> workLocations;

    private List<PutWall> putWalls;
}
