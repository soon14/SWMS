package com.swms.station.websocket.controller;


import com.google.common.collect.Maps;
import com.swms.station.websocket.config.GetHttpSessionConfigurator;
import com.swms.station.websocket.utils.HttpContext;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 这个类相当于一个ws协议的controller
 */
@Slf4j
@ServerEndpoint(value = "/websocket", configurator = GetHttpSessionConfigurator.class)
@Controller
public class StationWebSocketController {

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    private static final Map<String, StationWebSocketController> STATION_WEBSOCKET_MAP = Maps.newConcurrentMap();

    /**
     * 连接建立成功调用的方法
     * <p>
     * 这里有3种方式来将前端的 stationCode 带到这里来：
     * <p>
     * 1. 利用 javax.websocket.server.ServerEndpointConfig.Configurator 将 request.getHeaders() 塞到线程变量里面去。
     * 2. 和上面的方法差不多，只不过将这个 k-v 塞到 serverEndpointConfig.getUserProperties() 这个 map 里面，然后在 controller 里面可以同时在
     * Session session 和 EndpointConfig config 里面得到。要注意的是，这里发生了 map 的复制。
     * 3. 基于方法2，可以强行生成一个 httpsession。需要使用 javax.servlet.ServletRequestListener。
     * <p>
     * 目前采用了方法 2
     * <p>
     *
     * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     * @param config  这个参数里面如果要携带自定义的值的话，可以在 ServerEndpointConfig.Configurator 这里处理。
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) throws IOException {

        this.session = session;

        String stationCode;
        stationCode = (String) config.getUserProperties().get(HttpContext.STATION_CODE);

        if (stationCode == null) {
            Map<String, List<String>> parameterMap = session.getRequestParameterMap();
            List<String> stationCodes = parameterMap.get(HttpContext.STATION_CODE);
            if (ObjectUtils.isNotEmpty(stationCodes)) {
                stationCode = stationCodes.get(0);
            }
        }

        if (stationCode != null) {
            log.info("station: {} websocket is open and websocket id is: {}.", stationCode, session.getId());

            //操作台连接后，将websocket对象加入到map中，如果相同操作台Code的连进来，直接覆盖
            STATION_WEBSOCKET_MAP.put(stationCode, this);

        } else {
            log.warn("client pass no stationCode, close session");

            session.close();
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        log.info("websocket: {} close .", session == null ? "null" : session.getId());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("websocket: {} receive message: {}.", session.getId(), message);
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("websocket err: ", error);
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) {
        synchronized (this.session) {
            try {
                if (this.session.isOpen()) {
                    this.session.getBasicRemote().sendText(message == null ? "" : message);
                }
            } catch (IOException ioException) {
                log.error("station websocket send text error: ", ioException);
            }
        }
    }

    public boolean isOpen() {
        if (this.session == null) {
            log.debug("session is closed.");
            return false;
        }
        return this.session.isOpen();
    }

    public static StationWebSocketController getInstance(String stationCodeInfo) {
        return STATION_WEBSOCKET_MAP.get(stationCodeInfo);
    }

    public Session getSession() {
        return this.session;
    }

}

