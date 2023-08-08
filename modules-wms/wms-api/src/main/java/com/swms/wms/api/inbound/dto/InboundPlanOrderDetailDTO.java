package com.swms.wms.api.inbound.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Map;
import java.util.SortedMap;

@Data
public class InboundPlanOrderDetailDTO {

    private Long id;

    private Long inboundPlanOrderId;

    @Size(max = 64)
    private String containerCode;
    @Size(max = 64)
    private String containerSpecCode;
    @Size(max = 64)
    private String containerSlotCode;

    @Size(max = 64)
    private String boxNo;

    @NotNull
    @Min(1)
    private Integer qtyRestocked;
    private Integer qtyAccepted;
    private Integer qtyUnreceived;

    private Integer qtyAbnormal;
    @Size(max = 128)
    private String abnormalReason;
    @Size(max = 128)
    private String responsibleParty;

    @NotEmpty
    @Size(max = 64)
    private String skuCode;
    private Long skuId;
    @Size(max = 128)
    private String skuName;
    private String style;
    private String color;
    private String size;
    private String brand;

    private SortedMap<String, Object> batchAttributes;
    private Map<String, Object> extendFields;

}
