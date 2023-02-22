package com.swms.station.business.executor;

public interface HandlerExecutor {

    /**
     * 执行业务
     *
     * @param body
     */
    void execute(String apiCode, String body);
}
