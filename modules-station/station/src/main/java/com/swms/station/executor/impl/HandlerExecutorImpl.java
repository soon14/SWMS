package com.swms.station.executor.impl;

import com.google.common.base.Preconditions;
import com.swms.common.utils.constants.RedisConstants;
import com.swms.common.utils.utils.JsonUtils;
import com.swms.common.utils.utils.ValidatorUtils;
import com.swms.distribute.lock.DistributeLock;
import com.swms.station.api.ApiCodeEnum;
import com.swms.station.business.handler.BusinessHandlerFactory;
import com.swms.station.business.handler.IBusinessHandler;
import com.swms.station.executor.HandlerExecutor;
import com.swms.station.view.ViewHelper;
import com.swms.station.websocket.utils.StationWebSocketUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HandlerExecutorImpl implements HandlerExecutor {

    @Autowired
    private ViewHelper viewHelper;

    @Autowired
    private DistributeLock distributeLock;

    @Override
    @SuppressWarnings("unchecked")
    public void execute(ApiCodeEnum apiCode, String body, Long workStationId) {

        distributeLock.acquireLockIfThrows(RedisConstants.WORK_STATION_OPERATE_SYNC_LOCK + workStationId, 3000L);
        try {
            IBusinessHandler businessHandler = BusinessHandlerFactory.getHandler(apiCode);

            Object parameter;

            Class<?> parameterClass = businessHandler.getParameterClass();
            if (parameterClass.isAssignableFrom(String.class)) {
                parameter = body;
            } else {
                Preconditions.checkState(StringUtils.isNotEmpty(body));
                parameter = JsonUtils.string2Object(body, parameterClass);
                ValidatorUtils.validate(parameter);
            }

            businessHandler.execute(parameter, workStationId);

            viewHelper.buildView(apiCode, workStationId);

            StationWebSocketUtils.noticeWebStationStatusChanged(workStationId);
        } finally {
            distributeLock.releaseLock(RedisConstants.WORK_STATION_OPERATE_SYNC_LOCK + workStationId);
        }
    }
}
