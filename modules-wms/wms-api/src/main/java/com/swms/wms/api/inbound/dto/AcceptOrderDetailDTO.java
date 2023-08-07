package com.swms.wms.api.inbound.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.SortedMap;
import java.util.TreeMap;

@Data
public class AcceptOrderDetailDTO {

    private Long id;

    private Long acceptOrderId;

    @NotNull
    private Long inboundPlanOrderDetailId;

    private String boxNo;

    // if sku is loose , then they will be packed into a box
    private String packBoxNo;

    @NotEmpty
    private String targetContainerCode;
    @NotEmpty
    private String targetContainerSpecCode;
    @NotEmpty
    private String targetContainerSlotCode;

    @NotNull
    @Min(1)
    private Integer qtyAccepted;

    @NotEmpty
    private String skuCode;
    private String skuName;
    private String style;
    private String color;
    private String size;
    private String brand;

    private SortedMap<String, Object> batchAttributes = new TreeMap<>();

    private Long stationId;
}
