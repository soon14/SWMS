package com.swms.station.business;

import com.swms.station.api.ApiCodeEnum;

public interface IBusinessHandler {


    /**
     * 执行业务
     *
     * @param body
     *
     * @return
     */
    Object execute(String body);

    ApiCodeEnum getApiCode();

}
