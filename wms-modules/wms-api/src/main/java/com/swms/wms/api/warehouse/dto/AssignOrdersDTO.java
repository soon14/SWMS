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
public class AssignOrdersDTO {

    private List<Long> orderIds;
    private String stationCode;
    private String putWallSlotCode;
}
