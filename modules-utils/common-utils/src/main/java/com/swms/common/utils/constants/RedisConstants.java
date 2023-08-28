package com.swms.common.utils.constants;

public class RedisConstants {

    /**
     * topic
     */
    public static final String STATION_LISTEN_WORK_STATION_CONFIG_UPDATE = "station:listen:work:station:config:update";

    public static final String STATION_LISTEN_ORDER_ASSIGNED = "station:listen:order:assigned";

    public static final String PLUGIN_LISTEN_PLUGIN_MANAGEMENT = "plugin:listen:plugin:management";


    /**
     * Mdm module lock
     */
    public static final String BARCODE_PARSE_RULE_ADD_LOCK = "mdm:barcode:parse:rule:add:lock";

    public static final String BATCH_ATTRIBUTE_CONFIG_ADD_LOCK = "mdm:batch:attribute:config:add:lock";


    /**
     * Inbound module lock
     */
    public static final String INBOUND_PLAN_ORDER_ADD_LOCK = "wms:inbound:plan:order:add:lock";

    public static final String INBOUND_ACCEPT_OPERATE_LOCK = "wms:inbound:accept:operate:lock";


    /**
     * Station module lock
     */
    public static final String WORK_STATION_OPERATE_SYNC_LOCK = "station:work:station:operate:sync:lock";


}
