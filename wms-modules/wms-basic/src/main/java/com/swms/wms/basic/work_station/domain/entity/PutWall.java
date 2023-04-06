package com.swms.wms.basic.work_station.domain.entity;

import lombok.Data;

import java.util.List;

/**
 * Definition: SomeWhere that operators put items.
 */
@Data
public class PutWall {

    private Long id;
    private String stationCode;
    private String putWallCode;
    private String putWallName;
    private List<PutWallSlot> putWallSlots;

}
