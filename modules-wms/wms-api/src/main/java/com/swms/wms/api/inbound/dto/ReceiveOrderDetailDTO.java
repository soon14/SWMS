package com.swms.wms.api.inbound.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.SortedMap;
import java.util.TreeMap;

@Data
public class ReceiveOrderDetailDTO {

    private Long id;

    private Long inboundPlanOrderDetailId;
    private Long receiveOrderId;

    private String containerCode;
    private String containerSpecCode;
    private String containerSlotCode;

    @NotEmpty
    private String boxNo;

    @Min(1)
    @NotNull
    private Integer qtyReceived;

    @NotEmpty
    @Size(max = 64)
    private String skuCode;
    @Size(max = 128)
    private String skuName;
    private String style;
    private String color;
    private String size;
    private String brand;

    private String receiver;

    private SortedMap<String, Object> batchAttributes = new TreeMap<>();
}
