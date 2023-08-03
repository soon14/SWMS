package com.swms.wms.api.basic.dto;

import com.swms.wms.api.basic.constants.TerminalTypeEnum;
import com.swms.wms.api.basic.constants.WorkLocationTypeEnum;
import com.swms.wms.api.basic.constants.WorkStationOperationTypeEnum;
import com.swms.wms.api.basic.constants.WorkStationStatusEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkStationDTO {

    private Long id;

    @NotNull
    private String stationCode;
    @NotEmpty
    private String stationName;

    private WorkStationStatusEnum workStationStatus;

    @NotEmpty
    private String warehouseCode;
    @NotEmpty
    private String warehouseAreaCode;
    private boolean enable;
    private WorkStationOperationTypeEnum operationType;

    private List<WorkLocation<? extends WorkLocationSlot>> workLocations;
    private List<PutWallDTO> putWalls;
    private WorkStationConfigDTO workStationConfig;


    private Long version;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class WorkLocation<T extends WorkLocationSlot> {

        private String stationCode;
        /**
         * like SHELF, ROBOT, CONVEYOR and so on
         */
        private WorkLocationTypeEnum workLocationType;
        private String workLocationCode;
        private TerminalTypeEnum terminalType;

        private boolean enable;

        private List<T> workLocationSlots;

    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class WorkLocationSlot {

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
}
