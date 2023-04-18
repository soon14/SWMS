package com.swms.wms.api.inbound.dto;

import lombok.Data;

import java.util.SortedMap;

@Data
public class AcceptRecordDTO {

    private Long acceptOrderId;
    private Long acceptOrderDetailId;
    private String skuCode;
    private SortedMap<String, Object> batchAttributes;
    private Integer acceptQty;

    private String containerCode;
    private String containerSpecCode;
    private String containerSlotCode;
}
