package com.swms.station.business.executor.impl;

import com.swms.station.business.IBusinessHandler;
import com.swms.station.business.executor.BusinessHandlerExecutor;
import com.swms.station.business.handler.BusinessHandlerFactory;
import org.springframework.stereotype.Service;

@Service
public class BusinessHandlerExecutorImpl implements BusinessHandlerExecutor {
    @Override
    public void execute(String apiCode, String body) {
        IBusinessHandler businessHandler = BusinessHandlerFactory.getHandler(apiCode);
        businessHandler.execute(body);
    }
}
