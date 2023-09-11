package com.swms.common.utils.id;

import lombok.NoArgsConstructor;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
@NoArgsConstructor
public class OrderNoGenerator {

    private static final String INBOUND_PLAN_ORDER_NO_PREFIX = "IN_";
    private static AtomicInteger IN_INDEX = new AtomicInteger(1);

    private static final String RECEIVE_ORDER_NO_PREFIX = "RE_";
    private static final String ACCEPT_ORDER_NO_PREFIX = "APT_";
    private static final String OUTBOUND_PLAN_ORDER_NO_PREFIX = "OUT_";
    private static final String PICKING_ORDER_NO_PREFIX = "PICK_";

    private static Snowflake snowflake;

    @Autowired
    public OrderNoGenerator(Snowflake snowflake) {
        OrderNoGenerator.snowflake = snowflake;
    }

    public static String generationInboundPlanOrderNo() {
        return generateOrderNo(INBOUND_PLAN_ORDER_NO_PREFIX);
    }

    public static String generationOutboundPlanOrderNo() {
        return generateOrderNo(OUTBOUND_PLAN_ORDER_NO_PREFIX);
    }

    private static final FastDateFormat formatter = FastDateFormat.getInstance("yyyy-MM-dd_HH-mm-ss");


    /**
     * OrderNo = prefix + datacenterId + workId + "YYYY-MM-DD_HH-mm-ss" + INDEX
     *
     * @param prefix
     *
     * @return
     */
    private static String generateOrderNo(String prefix) {
        return prefix + snowflake.getDatacenterId() + snowflake.getWorkerId() +
            formatter.format(System.currentTimeMillis()) + "-" + IN_INDEX.getAndIncrement();
    }
}
