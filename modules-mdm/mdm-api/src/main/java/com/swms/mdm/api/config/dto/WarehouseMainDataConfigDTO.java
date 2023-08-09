package com.swms.mdm.api.config.dto;

import lombok.Data;

@Data
public class WarehouseMainDataConfigDTO {

    private boolean enablePutAwayRule = true;
    private boolean enableSkuWeighing = false;
    private boolean allowPutAwayOutside = false;

    private Boolean disableReceiving;
}
