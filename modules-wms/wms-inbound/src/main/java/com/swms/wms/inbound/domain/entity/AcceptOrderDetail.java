package com.swms.wms.inbound.domain.entity;

import com.swms.wms.inbound.application.check.IInboundOrderDetail;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@Data
public class AcceptOrderDetail implements IInboundOrderDetail {
    private Long id;

    private Long acceptOrderId;

    @NotNull
    private Long inboundPlanOrderDetailId;

    private String boxNo;

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
    private Long skuId;
    private String style;
    private String color;
    private String size;
    private String brand;

    private SortedMap<String, Object> batchAttributes = new TreeMap<>();

    private Map<String, Object> extendFields;

    private Long stationId;

}
