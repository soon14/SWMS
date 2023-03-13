package com.swms.wms.api.warehouse.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AssignOrdersDTO {

    private List<Long> orderIds;
    private String stationCode;
    private String putWallSlotCode;
}
