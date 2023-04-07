package com.swms.wms.api.basic.dto;

import com.swms.wms.api.basic.constants.WorkLocationTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkLocationDTO<T extends WorkLocationSlotDTO> {

    private String stationCode;
    /**
     * like SHELF, ROBOT, CONVEYOR and so on
     */
    private WorkLocationTypeEnum workLocationType;
    private String workLocationCode;

    private boolean enable;

    private List<T> workLocationSlots;

}
