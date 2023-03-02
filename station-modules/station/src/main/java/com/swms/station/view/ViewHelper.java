package com.swms.station.view;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.swms.station.api.ApiCodeEnum;
import com.swms.station.business.model.WorkStation;
import com.swms.station.business.model.WorkStationManagement;
import com.swms.station.view.handler.IViewHandler;
import com.swms.station.view.model.WorkStationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ViewHelper {

    private static final Map<String, WorkStationVO> workStationVOMap = Maps.newHashMap();

    @Autowired
    private List<IViewHandler> iViewHandlers;

    @Autowired
    private WorkStationManagement workStationManagement;

    public void buildView(ApiCodeEnum apiCode, String stationCode) {
        WorkStation workStation = workStationManagement.getWorkStation(stationCode);
        if (workStation == null) {
            removeWorkStationVO(stationCode);
            return;
        }

        WorkStationVO workStationVO = getWorkStationVO(stationCode);
        if (workStationVO == null) {
            workStationVO = new WorkStationVO();
            workStationVO.setStationCode(stationCode);

            workStationVOMap.put(stationCode, workStationVO);
        }

        List<IViewHandler> viewHandlers = getViewHandlers(apiCode);
        for (IViewHandler viewHandler : viewHandlers) {
            viewHandler.buildView(workStationVO, workStation);
        }

        updateWorkStationVO(workStationVO);
    }

    public List<IViewHandler> getViewHandlers(ApiCodeEnum apiCode) {
        return iViewHandlers;
    }

    public WorkStationVO getWorkStationVO(String stationCode) {
        return workStationVOMap.get(stationCode);
    }

    private void updateWorkStationVO(WorkStationVO workStationVO) {
        workStationVOMap.put(workStationVO.getStationCode(), workStationVO);
    }

    private void removeWorkStationVO(String stationCode) {
        workStationVOMap.put(stationCode, null);
    }
}
