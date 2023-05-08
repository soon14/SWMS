package com.swms.mdm.api.config.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BarcodeParseResult {
    private String fileName;
    private Object fileValue;
}
