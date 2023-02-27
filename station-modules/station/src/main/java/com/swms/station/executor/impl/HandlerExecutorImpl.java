package com.swms.station.executor.impl;

import com.swms.station.api.ApiCodeEnum;
import com.swms.station.business.handler.BusinessHandlerFactory;
import com.swms.station.business.handler.IBusinessHandler;
import com.swms.station.executor.HandlerExecutor;
import com.swms.station.view.ViewHelper;
import com.swms.station.websocket.utils.StationWebSocketUtils;
import org.springframework.stereotype.Service;

@Service
public class HandlerExecutorImpl implements HandlerExecutor {

    @Override
    public void execute(ApiCodeEnum apiCode, String body, String stationCode) {

        IBusinessHandler businessHandler = BusinessHandlerFactory.getHandler(apiCode);
        businessHandler.execute(body, stationCode);

        ViewHelper.buildView(apiCode, stationCode);

        StationWebSocketUtils.noticeWebStationStatusChanged(stationCode);
    }
}
