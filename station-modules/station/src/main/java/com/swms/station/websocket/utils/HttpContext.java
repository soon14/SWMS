package com.swms.station.websocket.utils;

public class HttpContext {

    protected HttpContext() {
    }

    public static final String STATION_CODE = "stationCode";

    private static final ThreadLocal<String> stationCodeContext = new ThreadLocal<>();

    public static void setStationCode(String stationCode) {
        stationCodeContext.set(stationCode);
    }

    public static void removeStationCode() {
        stationCodeContext.remove();
    }

    public static String getStationCode() {
        return stationCodeContext.get();
    }

}
