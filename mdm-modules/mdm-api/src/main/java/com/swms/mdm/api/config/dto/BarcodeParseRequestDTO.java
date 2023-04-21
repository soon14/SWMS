package com.swms.mdm.api.config.dto;

import com.swms.mdm.api.config.constants.BusinessFlowEnum;
import com.swms.mdm.api.config.constants.ExecuteTimeEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class BarcodeParseRequestDTO {

    @NotEmpty
    private String barcode;

    private String ownerCode;

    private List<KnownSku> knownSkus;

    @NotNull
    private BusinessFlowEnum businessFlow;
    @NotNull
    private ExecuteTimeEnum executeTime;

    @Data
    public static class KnownSku {

        @NotEmpty
        private String ownerCode;
        @NotEmpty
        private String skuCode;
        @NotEmpty
        private String brand;
    }
}
