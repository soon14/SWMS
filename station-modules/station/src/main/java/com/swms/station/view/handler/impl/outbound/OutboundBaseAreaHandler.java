package com.swms.station.view.handler.impl.outbound;

import com.swms.station.business.model.WorkStation;
import com.swms.station.view.handler.BaseAreaHandler;
import com.swms.station.view.model.WorkStationVO;
import org.springframework.stereotype.Service;

@Service
public class OutboundBaseAreaHandler extends BaseAreaHandler {

    @Override
    public void buildView(WorkStationVO workStationVO, WorkStation workStation) {
        super.buildView(workStationVO, workStation);
    }
}
