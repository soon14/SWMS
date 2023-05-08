package com.swms.station.view.handler;

import com.swms.station.business.model.WorkStation;
import com.swms.station.business.model.WorkStationManagement;
import com.swms.station.view.model.WorkStationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class BaseAreaHandler implements IViewHandler {

    @Override
    public void buildView(WorkStationVO workStationVO, WorkStation workStation) {
        workStationVO.setWorkStationStatus(workStation.getWorkStationStatus());
        workStationVO.setOperationType(workStation.getOperationType());
    }
}
