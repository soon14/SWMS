package com.swms.wms.api.inbound.constants;

import com.swms.utils.dictionary.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReceiveOrderCreateTypeEnum implements IEnum {

    PDA("pda", "pda"),
    TRANSFER("transfer", "transfer");

    private String value;
    private String label;
}
