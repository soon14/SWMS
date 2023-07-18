package com.swms.station.executor.impl;

import com.swms.station.api.ApiCodeEnum;
import com.swms.station.business.handler.BusinessHandlerFactory;
import com.swms.station.business.handler.IBusinessHandler;
import com.swms.station.executor.HandlerExecutor;
import com.swms.station.view.ViewHelper;
import com.swms.station.websocket.utils.StationWebSocketUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HandlerExecutorImpl implements HandlerExecutor {

    @Autowired
    private ViewHelper viewHelper;

    @Override
    public void execute(ApiCodeEnum apiCode, String body, Long workStationId) {

        IBusinessHandler businessHandler = BusinessHandlerFactory.getHandler(apiCode);
        businessHandler.execute(body, workStationId);

        viewHelper.buildView(apiCode, workStationId);

        StationWebSocketUtils.noticeWebStationStatusChanged(workStationId);
    }
}
