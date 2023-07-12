package com.swms.wms.basic.work_station.controller.parameter;

import com.google.common.collect.Lists;
import com.swms.wms.api.basic.constants.TerminalTypeEnum;
import com.swms.wms.api.basic.constants.WorkLocationTypeEnum;
import com.swms.wms.api.basic.constants.WorkStationOperationTypeEnum;
import com.swms.wms.api.basic.dto.WorkStationDTO;
import lombok.Data;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

@Data
public class WorkStationVO {

    private Long id;
    private String stationCode;
    private String stationName;

    private String warehouseCode;
    private String warehouseAreaCode;

    private WorkStationOperationTypeEnum operationType;

    private List<WorkStationOperationTypeEnum> allowOperationTypes;
    private Long workStationRuleId;


    // workLocation
    private WorkLocationTypeEnum workLocationType;
    private String workLocationCode;
    private TerminalTypeEnum terminalType;


    private List<WorkStationDTO.WorkLocation<? extends WorkStationDTO.WorkLocationSlot>> workLocations;

    private boolean deleted;
    private boolean enable;
    private Long version;

    public List<WorkStationDTO.WorkLocation<? extends WorkStationDTO.WorkLocationSlot>> getWorkLocations() {

        return Lists.newArrayList(WorkStationDTO.WorkLocation.builder()
            .stationCode(stationCode)
            .workLocationCode(stationCode + "_loc")
            .workLocationType(workLocationType)
            .terminalType(terminalType)
            .enable(enable)
            .build());
    }


    public void flatWorkLocation() {
        if (workLocations == null) {
            return;
        }

        WorkStationDTO.WorkLocation<? extends WorkStationDTO.WorkLocationSlot> workLocation = workLocations.get(0);
        this.workLocationType = workLocation.getWorkLocationType();
        this.terminalType = workLocation.getTerminalType();
    }

    public void setAllowOperationTypes(String allowOperationTypeStr) {
        if (StringUtils.isEmpty(allowOperationTypeStr)) {
            return;
        }
        this.allowOperationTypes = Arrays.stream(allowOperationTypeStr.split(","))
            .map(v -> EnumUtils.getEnum(WorkStationOperationTypeEnum.class, v)).toList();
    }
}
