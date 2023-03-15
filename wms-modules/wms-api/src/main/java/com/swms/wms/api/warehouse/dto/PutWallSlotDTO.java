package com.swms.wms.api.warehouse.dto;

import com.swms.wms.api.warehouse.constants.PutWallSlotStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PutWallSlotDTO {

    private String putWallCode;
    private String stationCode;

    private String slotCode;
    private Integer level;
    private Integer bay;
    private boolean enable;

    private List<Long> orderIds;

    private PutWallSlotStatusEnum putWallSlotStatus;

    private String transferContainerCode;
}
