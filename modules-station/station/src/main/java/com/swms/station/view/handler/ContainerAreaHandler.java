package com.swms.station.view.handler;

import com.swms.station.domain.persistence.entity.WorkStation;
import com.swms.station.view.model.WorkStationVO;
import org.springframework.stereotype.Service;

@Service
public abstract class ContainerAreaHandler implements IViewHandler {

    @Override
    public void buildView(WorkStationVO workStationVO, WorkStation workStation) {
        workStationVO.getWorkLocationArea().setWorkLocationViews(workStation.getWorkLocations());
    }
}
