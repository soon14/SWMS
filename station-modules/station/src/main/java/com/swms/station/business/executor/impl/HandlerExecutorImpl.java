package com.swms.station.business.executor.impl;

import com.swms.station.business.IBusinessHandler;
import com.swms.station.business.executor.HandlerExecutor;
import com.swms.station.business.handler.BusinessHandlerFactory;
import org.springframework.stereotype.Service;

@Service
public class HandlerExecutorImpl implements HandlerExecutor {
    @Override
    public Object execute(String apiCode, String body) {
        IBusinessHandler businessHandler = BusinessHandlerFactory.getHandler(apiCode);
        return businessHandler.execute(body);
    }
}
