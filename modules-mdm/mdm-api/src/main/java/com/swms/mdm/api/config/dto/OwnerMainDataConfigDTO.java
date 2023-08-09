package com.swms.mdm.api.config.dto;

import lombok.Data;

@Data
public class OwnerMainDataConfigDTO {
    private boolean allowInboundBoxNoRepeat = true;

    private boolean inboundCallbackSN = true;
}
