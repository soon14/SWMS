package com.swms.station.websocket.config;

import com.swms.station.websocket.utils.HttpContext;
import jakarta.websocket.HandshakeResponse;
import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.ServerEndpointConfig;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 处理websocket header 中传的参数
 * https://www.cnblogs.com/hellxz/p/8063867.html
 * https://www.cnblogs.com/coder163/p/8605645.html
 *
 * @author sws
 * @since 2021-04-15 21:31
 */
@Slf4j
public class GetHttpSessionConfigurator extends ServerEndpointConfig.Configurator {

    /**
     * 重写修改握手方法
     */
    @Override
    public void modifyHandshake(ServerEndpointConfig serverEndpointConfig, HandshakeRequest request, HandshakeResponse response) {

        List<String> strings = request.getHeaders().get(HttpContext.WORK_STATION_ID);

        if (strings == null || strings.size() != 1) {
            return;
        }

        String stationCode = strings.get(0);
        serverEndpointConfig.getUserProperties().put(HttpContext.WORK_STATION_ID, stationCode);
        super.modifyHandshake(serverEndpointConfig, request, response);
    }


}
