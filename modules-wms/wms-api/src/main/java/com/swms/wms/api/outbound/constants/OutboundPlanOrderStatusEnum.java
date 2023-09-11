package com.swms.wms.api.outbound.constants;

import com.swms.common.utils.dictionary.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OutboundPlanOrderStatusEnum implements IEnum {
    NEW("NEW", "新单据"),
    SHORT_WAITING("SHORT_WAITING", "缺货等待"),
    ASSIGNED("ASSIGNED", "分配完成(库区)"),
    DISPATCHED("DISPATCHED", "派单完成"),
    PICKING("PICKING", "拣货中"),
    PICKED("PICKED", "拣货完成"),
    CANCELED("CANCELED", "已取消");

    private String value;
    private String label;
}
