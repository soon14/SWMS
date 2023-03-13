package com.swms.wms.api.warehouse.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ReleasePutWallSlotsDTO {
    private String stationCode;
    private List<String> putWallSlotCodes;
}
