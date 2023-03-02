package com.swms.station.view.handler;

import com.swms.station.business.model.WorkStation;
import com.swms.station.view.model.WorkStationVO;

public interface IViewHandler {
    void buildView(WorkStationVO workStationVO, WorkStation workStation);
}
