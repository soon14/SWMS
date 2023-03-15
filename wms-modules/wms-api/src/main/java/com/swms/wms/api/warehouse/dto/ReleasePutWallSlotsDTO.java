package com.swms.wms.api.warehouse.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReleasePutWallSlotsDTO {
    private String stationCode;
    private List<String> putWallSlotCodes;
}
