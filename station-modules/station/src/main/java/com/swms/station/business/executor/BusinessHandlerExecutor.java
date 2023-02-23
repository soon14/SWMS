package com.swms.station.business.executor;

public interface BusinessHandlerExecutor {

    /**
     * 执行业务
     *
     * @param body
     */
    void execute(String apiCode, String body);
}
