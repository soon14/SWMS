package com.swms.station.business.model;

import com.swms.wms.api.warehouse.dto.WorkLocationDTO;
import com.swms.wms.api.warehouse.dto.WorkLocationSlotDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * extends WorkLocationDTO to add the arrived container on the location.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkLocationExtend extends WorkLocationDTO<WorkLocationExtend.WorkLocationSlotExtend> {

    private List<WorkLocationSlotExtend> workLocationSlots;

    @EqualsAndHashCode(callSuper = true)
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class WorkLocationSlotExtend extends WorkLocationSlotDTO {
        private ArrivedContainer arrivedContainer;
    }

}
