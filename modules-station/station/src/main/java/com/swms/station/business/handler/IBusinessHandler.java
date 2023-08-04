package com.swms.station.business.handler;

import com.swms.station.api.ApiCodeEnum;

public interface IBusinessHandler<T> {


    /**
     * 执行业务
     *
     * @param body          parameters
     * @param workStationId work station ID
     *
     * @return
     */
    void execute(T body, Long workStationId);

    ApiCodeEnum getApiCode();

    Class<T> getParameterClass();

}
