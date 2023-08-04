package com.swms.station.view;

import com.google.common.collect.Maps;
import com.swms.station.api.ApiCodeEnum;
import com.swms.station.domain.persistence.entity.WorkStation;
import com.swms.station.domain.service.WorkStationService;
import com.swms.station.view.handler.IViewHandler;
import com.swms.station.view.model.WorkStationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ViewHelper {

    private static final Map<Long, WorkStationVO> workStationVOMap = Maps.newHashMap();

    @Autowired
    private List<IViewHandler> iViewHandlers;

    @Autowired
    private WorkStationService workStationManagement;

    public void buildView(ApiCodeEnum apiCode, Long workStationId) {
        WorkStation workStation = workStationManagement.getWorkStation(workStationId);
        if (workStation == null) {
            removeWorkStationVO(workStationId);
            return;
        }

        WorkStationVO workStationVO = getWorkStationVO(workStationId);
        if (workStationVO == null) {
            workStationVO = new WorkStationVO();
            workStationVO.setWorkStationId(workStationId);

            workStationVOMap.put(workStationId, workStationVO);
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

    public WorkStationVO getWorkStationVO(Long stationCode) {
        return workStationVOMap.get(stationCode);
    }

    private void updateWorkStationVO(WorkStationVO workStationVO) {
        workStationVOMap.put(workStationVO.getWorkStationId(), workStationVO);
    }

    private void removeWorkStationVO(Long workStationId) {
        workStationVOMap.put(workStationId, null);
    }
}
