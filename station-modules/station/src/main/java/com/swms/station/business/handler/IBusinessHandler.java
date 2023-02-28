package com.swms.station.business.handler;

import com.swms.station.api.ApiCodeEnum;

public interface IBusinessHandler {


    /**
     * 执行业务
     *
     * @param s
     * @param body
     *
     * @return
     */
    Object execute(String body, String stationCode);

    ApiCodeEnum getApiCode();

}
