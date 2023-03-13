package com.swms.wms.api.warehouse.dto;

import com.swms.wms.api.warehouse.constants.PutWallSlotStatus;
import lombok.Data;

import java.util.List;

@Data
public class PutWallSlotDTO {

    private String putWallCode;

    private String slotCode;
    private String groupCode;
    private Integer level;
    private Integer bay;
    private boolean enable;

    private List<Long> orderIds;

    private PutWallSlotStatus putWallSlotStatus;

    private String transferContainerCode;
}
