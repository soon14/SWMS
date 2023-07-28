package com.swms.station.view.handler;

import com.swms.station.business.model.WorkStation;
import com.swms.station.view.model.WorkStationVO;
import org.springframework.stereotype.Service;

@Service
public abstract class PutWallAreaHandler implements IViewHandler {

    @Override
    public void buildView(WorkStationVO workStationVO, WorkStation workStation) {
        workStationVO.getPutWallArea().setPutWallViews(workStation.getPutWalls());
    }
}
