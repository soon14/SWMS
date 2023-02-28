package com.swms.station.view.handler;

import com.swms.station.business.model.WorkStation;
import com.swms.station.business.model.WorkStationManagement;
import com.swms.station.view.model.WorkStationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class PutWallAreaHandler implements IViewHandler {

    @Autowired
    private WorkStationManagement workStationManagement;

    @Override
    public void buildView(WorkStationVO workStationVO) {
        WorkStation workStation = workStationManagement.getWorkStation(workStationVO.getStationCode());
        workStationVO.getPutWallArea().setPutWallViews(workStation.getPutWalls());
    }
}
