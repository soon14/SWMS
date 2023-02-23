package com.swms.station.executor.impl;

import com.swms.station.business.IBusinessHandler;
import com.swms.station.business.handler.BusinessHandlerFactory;
import com.swms.station.executor.HandlerExecutor;
import org.springframework.stereotype.Service;

@Service
public class HandlerExecutorImpl implements HandlerExecutor {
    @Override
    public void execute(String apiCode, String body) {

        IBusinessHandler businessHandler = BusinessHandlerFactory.getHandler(apiCode);
        businessHandler.execute(body);

        // execute view handler
        //TODO
    }
}
