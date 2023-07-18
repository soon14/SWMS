package com.swms.wms.basic.work_station.domain.entity;

import com.swms.wms.api.basic.dto.PutWallDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Definition: SomeWhere that operators put items.
 */
@Data
@NoArgsConstructor
public class PutWall {

    private Long id;
    private Long workStationId;
    private String putWallCode;
    private String putWallName;
    private String containerSpecCode;
    private Long version;
    private List<PutWallDTO.PutWallSlot> putWallSlots;

    public PutWall(Long workStationId, String putWallCode, String putWallName, String containerSpecCode, @NotNull List<PutWallDTO.PutWallSlot> putWallSlots) {
        this.workStationId = workStationId;
        this.putWallCode = putWallCode;
        this.putWallName = putWallName;
        this.putWallSlots = putWallSlots;
        this.containerSpecCode = containerSpecCode;

        this.putWallSlots.forEach(v -> v.initPutWallSlot(this.putWallCode, this.workStationId));
    }

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
