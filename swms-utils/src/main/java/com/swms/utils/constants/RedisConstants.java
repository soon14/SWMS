package com.swms.utils.constants;

public class RedisConstants {

    /**
     * cache
     */
    public static final String STATION_LISTEN_WORK_STATION_CONFIG_UPDATE = "station:listen:work:station:config:update";

    public static final String STATION_LISTEN_ORDER_ASSIGNED = "station:listen:order:assigned";


    /**
     * lock
     */
    public static final String BARCODE_PARSE_RULE_ADD_LOCK = "mdm:barcode:parse:rule:add:lock";

    public static final String BATCH_ATTRIBUTE_CONFIG_ADD_LOCK = "mdm:batch:attribute:config:add:lock";

    public static final String INBOUND_PLAN_ORDER_ADD_LOCK = "wms:inbound:plan:order:add:lock";


}
