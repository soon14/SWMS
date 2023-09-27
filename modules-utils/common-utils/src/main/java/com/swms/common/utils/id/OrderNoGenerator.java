package com.swms.common.utils.id;

import com.swms.common.utils.utils.RedisUtils;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@NoArgsConstructor
@ConditionalOnBean(RedisUtils.class)
public class OrderNoGenerator {

    private static final String INBOUND_PLAN_ORDER_NO_PREFIX = "IN_";
    private static final String RECEIVE_ORDER_NO_PREFIX = "RE_";
    private static final String ACCEPT_ORDER_NO_PREFIX = "APT_";

    private static final String OUTBOUND_PLAN_ORDER_NO_PREFIX = "OUT_";
    private static final String OUTBOUND_WAVE_NO_PREFIX = "WAVE_";
    private static final String PICKING_ORDER_NO_PREFIX = "PICK_";

    private static final Map<String, Long> INDEX_MAP = new ConcurrentHashMap<>();
    private static final Map<String, Integer> COUNT_MAP = new ConcurrentHashMap<>();

    // Number of indexes to fetch from Redis at once
    private static final int CACHE_SIZE = 20;


    private static RedisUtils redisUtils;

    @Autowired
    public OrderNoGenerator(RedisUtils redisUtils) {
        OrderNoGenerator.redisUtils = redisUtils;
    }

    public static String generationInboundPlanOrderNo() {
        return generateOrderNo(INBOUND_PLAN_ORDER_NO_PREFIX);
    }

    public static String generationOutboundPlanOrderNo() {
        return generateOrderNo(OUTBOUND_PLAN_ORDER_NO_PREFIX);
    }

    public static String generationOutboundWaveNo() {
        return generateOrderNo(OUTBOUND_WAVE_NO_PREFIX);
    }

    public static String generationPickingOrderNo() {
        return generateOrderNo(PICKING_ORDER_NO_PREFIX);
    }

    private static final FastDateFormat formatter = FastDateFormat.getInstance("yyyy-MM-dd");

    /**
     * OrderNo = prefix + datacenterId + workId + "YYYY-MM-DD_HH-mm-ss" + INDEX
     *
     * @param prefix
     *
     * @return
     */
    private static String generateOrderNo(String prefix) {

        String key = prefix + formatter.format(System.currentTimeMillis());
        return prefix + formatter.format(System.currentTimeMillis()) + "-" + generateIndex(key);
    }

    private static String generateIndex(String key) {

        if (!COUNT_MAP.containsKey(key) || COUNT_MAP.get(key) == 0) {
            long index = redisUtils.getAndIncrement(key, CACHE_SIZE);
            INDEX_MAP.put(key, index);
            COUNT_MAP.put(key, CACHE_SIZE);
        }

        Long index = INDEX_MAP.get(key);
        COUNT_MAP.put(key, COUNT_MAP.get(key) - 1);
        INDEX_MAP.put(key, index + 1);
        return String.format("%06d", index);
    }
}
