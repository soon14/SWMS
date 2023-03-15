package com.swms.station.business.model;

import com.swms.wms.api.warehouse.dto.WorkLocationDTO;
import com.swms.wms.api.warehouse.dto.WorkLocationSlotDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * extends WorkLocationDTO to add the arrived container on the location.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WorkLocationExtend extends WorkLocationDTO<WorkLocationExtend.WorkLocationSlotExtend> {

    private List<WorkLocationSlotExtend> workLocationSlots;

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class WorkLocationSlotExtend extends WorkLocationSlotDTO {
        private ArrivedContainer arrivedContainer;
    }

}
