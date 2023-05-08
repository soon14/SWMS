package com.swms.wms.basic.work_station.domain.entity;

import com.swms.utils.exception.WmsException;
import com.swms.wms.api.basic.constants.WorkStationOperationTypeEnum;
import com.swms.wms.api.basic.constants.WorkStationStatusEnum;
import com.swms.wms.api.basic.dto.WorkStationDTO;
import lombok.Data;

import java.util.List;

@Data
public class WorkStation {

    private Long id;
    private String stationCode;

    private WorkStationStatusEnum workStationStatus;

    private String warehouseCode;
    private String warehouseAreaCode;
    private boolean enable;

    private WorkStationOperationTypeEnum operationType;

    private List<WorkStationDTO.WorkLocation<? extends WorkStationDTO.WorkLocationSlot>> workLocations;

    private boolean deleted;

    private Long version;

    public void enable() {
        this.enable = true;
    }

    public void disable() {
        if (workStationStatus != WorkStationStatusEnum.OFFLINE) {
            throw new WmsException("work station is not offline, can not enable");
        }
        this.enable = false;
    }

    public void delete() {
        if (workStationStatus != WorkStationStatusEnum.OFFLINE) {
            throw new WmsException("work station is not offline, can not enable");
        }
        this.deleted = true;
    }

    public void online(WorkStationOperationTypeEnum operationType) {
        if (workStationStatus != WorkStationStatusEnum.OFFLINE) {
            throw new WmsException("work station is not offline, can not online");
        }
        this.operationType = operationType;
        this.workStationStatus = WorkStationStatusEnum.ONLINE;
    }

    public void offline() {
        this.workStationStatus = WorkStationStatusEnum.OFFLINE;
    }

    public void pause() {
        this.workStationStatus = WorkStationStatusEnum.PAUSED;
    }

    public void resume() {
        this.workStationStatus = WorkStationStatusEnum.ONLINE;
    }

    /**
     * definition: a place that robots working
     */
    @Data
    public static class WorkStationConfig {
        private Long id;
        private String stationCode;
    }
}
