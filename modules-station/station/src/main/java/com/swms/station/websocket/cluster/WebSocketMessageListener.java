package com.swms.station.websocket.cluster;

import com.swms.common.utils.constants.RedisConstants;
import com.swms.mq.redis.RedisListener;
import com.swms.station.websocket.utils.StationWebSocketUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 为了解决websocket集群的问题，利用Redis的广播消息，
 * 将要发送的websocket message推送到Redis.
 * 然后所有的机器消费到消息后根据消息体的stationCode来决定哪个机器真正的推送消息。
 */
@Slf4j
@Component
public class WebSocketMessageListener {

    @RedisListener(topic = RedisConstants.STATION_LISTEN_STATION_WEBSOCKET)
    public void onMessage(String workStationId) {

        if (workStationId == null) {
            log.error("received work station id is null");
            return;
        }
        StationWebSocketUtils.noticeWebStationStatusChanged(Long.parseLong(workStationId));
    }
}
