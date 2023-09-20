package com.swms.wms.api.outbound.dto;

import com.swms.wms.api.outbound.constants.OutboundPlanOrderStatusEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Data
public class OutboundPlanOrderDTO {

    @NotEmpty
    private String warehouseCode;
    @NotEmpty
    private String ownerCode;

    private String waveNo;
    @NotEmpty
    private String customerOrderNo;
    private String customerOrderType;
    private String currierCode;
    private String waybillNo;
    private String origPlatformCode;

    private Long expiredTime;
    private Integer priority;

    private String orderNo;

    private Integer skuKindNum;
    private Integer totalQty;

    private OutboundPlanOrderStatusEnum outboundPlanOrderStatus;

    private boolean abnormal;
    private String abnormalReason;

    private Map<String, String> reservedFields;

    @NotEmpty
    private List<OutboundPlanOrderDetailDTO> details;

    private Long version;

    @Data
    public static class OutboundPlanOrderDetailDTO {

        @NotEmpty
        private String skuCode;
        private String skuName;

        private Map<String, Object> batchAttributes;

        @NotNull
        @Min(1)
        private Integer qtyRequired;
        private Integer qtyActual;
    }
}
