package com.swms.station.business.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Definition: SomeWhere that operators put items.
 */
@Data
public class PutWall {

    private String putWallCode;
    private List<PutWallSlot> putWallSlots;

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class PutWallSlot extends Slot {
        private String transferContainerCode;
        private List<Long> orderIds;
    }
}
