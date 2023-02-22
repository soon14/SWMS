package com.swms.station.business;

public interface IBusinessHandler {


    /**
     * 执行业务
     *
     * @param body
     *
     * @return
     */
    Object execute(String body);

    String getApiCode();

}
