package com.swms.wms.basic.work_station.domain.service.impl;

import com.swms.wms.api.basic.constants.WorkStationStatusEnum;
import com.swms.wms.basic.work_station.domain.entity.PutWall;
import com.swms.wms.basic.work_station.domain.entity.WorkStation;
import com.swms.wms.basic.work_station.domain.repository.WorkStationRepository;
import com.swms.wms.basic.work_station.domain.service.PutWallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PutWallServiceImpl implements PutWallService {

    @Autowired
    private WorkStationRepository workStationRepository;

    @Override
    public boolean checkDisablePutWall(PutWall putWall) {
        WorkStation workStation = workStationRepository.findById(putWall.getWorkStationId());
        return workStation == null || workStation.getWorkStationStatus() == WorkStationStatusEnum.OFFLINE;
    }

    @Override
    public boolean checkUpdatePutWall(PutWall putWall) {
        return checkDisablePutWall(putWall);
    }
}
