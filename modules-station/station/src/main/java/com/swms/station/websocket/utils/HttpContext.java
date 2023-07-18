package com.swms.station.websocket.utils;

public class HttpContext {

    protected HttpContext() {
    }

    public static final String WORK_STATION_ID = "stationCode";

    private static final ThreadLocal<Long> stationCodeContext = new ThreadLocal<>();

    public static void setWorkStationId(Long workStationId) {
        stationCodeContext.set(workStationId);
    }

    public static void removeStationCode() {
        stationCodeContext.remove();
    }

    public static Long getWorkStationId() {
        return stationCodeContext.get();
    }

}
