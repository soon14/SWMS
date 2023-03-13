package com.swms.wms.api.warehouse.dto;

import lombok.Data;

import java.util.List;

@Data
public class PutWallDTO {

    private String stationCode;
    private String putWallCode;
    private String putWallName;
    private List<PutWallSlotDTO> putWallSlots;
}
