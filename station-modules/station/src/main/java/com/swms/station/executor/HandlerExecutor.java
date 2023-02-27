package com.swms.station.executor;

import com.swms.station.api.ApiCodeEnum;

public interface HandlerExecutor {

    void execute(ApiCodeEnum apiCode, String body);

}
