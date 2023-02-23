package com.swms.station.business.model;

import lombok.Data;

import java.util.List;

/**
 * Definition: SomeWhere that operators put items.
 */
@Data
public class PutWall {

    private String putWallCode;

    private List<PutWallSlot> putWallSlots;

    @Data
    private static class PutWallSlot {
        private String putWallSlotCode;
        private Integer level;
        private Integer bay;
        private String transferContainerCode;
    }
}
