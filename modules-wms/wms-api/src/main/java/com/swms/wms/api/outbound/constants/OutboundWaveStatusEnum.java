package com.swms.wms.api.outbound.constants;

import com.swms.common.utils.dictionary.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OutboundWaveStatusEnum implements IEnum {

    NEW("NEW", "新单据"),
    PROCESSING("PROCESSING", "处理中"),
    DONE("DONE", "完成");

    private String value;
    private String label;
}
