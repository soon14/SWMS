package com.swms.wms.api.inbound.constants;

import com.swms.common.utils.dictionary.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 入库单类型枚举类型
 *
 * @author krystal
 * @date 2023/08/21
 */
@Getter
@AllArgsConstructor
public enum InboundOrderTypeEnum implements IEnum {


    /**
     * 通知订单
     */
    PLAN_ORDER("plan_order", "plan_order"),
    /**
     * 收货单
     */
    RECEIPT_ORDER("receipt_order", "receipt_order"),
    /**
     * 质量检验单
     */
    QUALITY_INSPECTION_ORDER("quality_inspection_order", "quality_inspection_order"),
    /**
     * 验收订单
     */
    ACCEPT_ORDER("accept_order", "accept_order"),
    /**
     * 上架任务
     */
    PUT_AWAY_TASK("put_away_task", "put_away_task")


    ;

    /**
     * 值
     */
    private final String value;

    /**
     * 标签
     */
    private final String label;


}
