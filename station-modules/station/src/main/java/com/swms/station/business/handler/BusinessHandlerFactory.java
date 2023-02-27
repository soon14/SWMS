package com.swms.station.business.handler;

import com.swms.station.api.ApiCodeEnum;
import com.swms.station.business.IBusinessHandler;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BusinessHandlerFactory implements InitializingBean {

    private static final Map<ApiCodeEnum, IBusinessHandler> map = new HashMap<>();

    @Autowired
    private List<IBusinessHandler> handlerList;

    @Override
    public void afterPropertiesSet() {
        for (IBusinessHandler handler : handlerList) {
            map.put(handler.getApiCode(), handler);
        }
    }

    public static IBusinessHandler getHandler(ApiCodeEnum apiType) {
        return map.get(apiType);
    }
}
