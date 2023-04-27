package com.swms.wms.basic.work_station.domain.entity;

import com.swms.wms.api.basic.dto.PutWallDTO;
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
    private String containerSpecCode;
    private List<PutWallDTO.PutWallSlot> putWallSlots;

    private boolean deleted;

    private boolean enable;

    public void enable() {
        this.enable = true;
    }

    public void disable() {
        this.enable = false;
    }

    public void delete() {
        this.deleted = true;
    }
}
