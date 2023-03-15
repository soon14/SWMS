package com.swms.wms.api.warehouse.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkLocationSlotDTO {

    private String workLocationCode;

    // a set of location slots that the container can be put into in a location.
    // it is a logical definition.
    // like robot code is the groupCode of robot, cache shelf code is the groupCode of cache shelf.
    private String groupCode;

    private String slotCode;
    private Integer level;
    private Integer bay;
    private boolean enable;
}
