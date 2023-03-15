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
public class PutWallDTO {

    private String stationCode;
    private String putWallCode;
    private String putWallName;
    private List<PutWallSlotDTO> putWallSlots;
}
