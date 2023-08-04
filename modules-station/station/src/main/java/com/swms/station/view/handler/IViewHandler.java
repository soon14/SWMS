package com.swms.station.view.handler;

import com.swms.station.domain.persistence.entity.WorkStation;
import com.swms.station.view.model.WorkStationVO;

public interface IViewHandler {
    void buildView(WorkStationVO workStationVO, WorkStation workStation);
}
