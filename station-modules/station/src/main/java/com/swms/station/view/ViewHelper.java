package com.swms.station.view;

import com.google.common.collect.Maps;
import com.swms.station.api.ApiCodeEnum;
import com.swms.station.view.handler.IViewHandler;
import com.swms.station.view.model.WorkStationVO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ViewHelper {

    private static Map<String, WorkStationVO> workStationVOMap = Maps.newHashMap();

    private ViewHelper() {
    }

    public static void buildView(ApiCodeEnum apiCode, String stationCode) {

        WorkStationVO workStationVO = getWorkStationVO(stationCode);
        if (workStationVO == null) {
            workStationVO = new WorkStationVO();
            workStationVO.setStationCode(stationCode);
        }

        List<IViewHandler> viewHandlers = getViewHandlers(apiCode);
        for (IViewHandler viewHandler : viewHandlers) {
            viewHandler.buildView(workStationVO);
        }

        updateWorkStationVO(workStationVO);
    }

    public static List<IViewHandler> getViewHandlers(ApiCodeEnum apiCode) {
        return new ArrayList<>();
    }

    public static WorkStationVO getWorkStationVO(String stationCode) {
        return workStationVOMap.get(stationCode);
    }

    private static void updateWorkStationVO(WorkStationVO workStationVO) {
        workStationVOMap.put(workStationVO.getStationCode(), workStationVO);
    }
}
