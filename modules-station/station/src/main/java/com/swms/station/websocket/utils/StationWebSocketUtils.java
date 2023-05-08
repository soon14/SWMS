package com.swms.station.websocket.utils;

import com.swms.station.websocket.controller.StationWebSocketController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StationWebSocketUtils {
    protected StationWebSocketUtils() {
    }

    /**
     * 自定义发送
     *
     * @param stationCode 操作台Code
     * @param message     消息
     */
    public static void sendInfo(String stationCode, String message) {

        StationWebSocketController stationWebSocketController = StationWebSocketController.getInstance(stationCode);

        if (stationWebSocketController != null) {
            //增加这个日志主要是为了查看什么时间接收到kafka的消息
            log.info("station: {} send message to websocket: {}.", stationCode,
                stationWebSocketController.getSession() == null ? "NULL" : stationWebSocketController.getSession().getId());

            stationWebSocketController.sendMessage(message);
        } else {
            log.debug("StationWebSocketUtils STATION_WEBSOCKET does not exist! station: {}, message:{}", stationCode, message);
        }

    }

    /**
     * notice web that status is changed.
     *
     * @param stationCode
     */
    public static void noticeWebStationStatusChanged(String stationCode) {
        sendInfo(stationCode, "changed");
    }
}
