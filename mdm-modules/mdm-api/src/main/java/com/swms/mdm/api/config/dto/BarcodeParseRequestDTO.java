package com.swms.mdm.api.config.dto;

import com.swms.mdm.api.config.constants.BusinessFlowEnum;
import com.swms.mdm.api.config.constants.ExecuteTimeEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class BarcodeParseRequestDTO {

    @NotNull
    private String barcode;

    private String ownerCode;

    private List<KnownSku> knownSkus;

    @NotNull
    private BusinessFlowEnum businessFlow;
    @NotNull
    private ExecuteTimeEnum executeTime;

    @Data
    public static class KnownSku {

        @NotNull
        private String ownerCode;
        @NotNull
        private String skuCode;
        @NotNull
        private String brand;
    }
}
