package com.swms.station.executor.impl;

import com.swms.station.api.ApiCodeEnum;
import com.swms.station.business.IBusinessHandler;
import com.swms.station.business.handler.BusinessHandlerFactory;
import com.swms.station.executor.HandlerExecutor;
import com.swms.station.view.ViewHelper;
import com.swms.station.view.handler.IViewHandler;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HandlerExecutorImpl implements HandlerExecutor {
    @Override
    public void execute(ApiCodeEnum apiCode, String body) {

        IBusinessHandler businessHandler = BusinessHandlerFactory.getHandler(apiCode);
        businessHandler.execute(body);

        List<IViewHandler> viewHandlers = ViewHelper.getViewHandlers(apiCode);
        viewHandlers.forEach(IViewHandler::buildView);
    }
}
