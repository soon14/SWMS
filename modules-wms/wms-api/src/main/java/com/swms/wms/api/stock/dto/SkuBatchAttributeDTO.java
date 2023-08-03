package com.swms.wms.api.stock.dto;

import lombok.Data;

import java.util.List;
import java.util.TreeMap;

@Data
public class SkuBatchAttributeDTO {

    private Long id;

    private Long skuId;

    private TreeMap<String, Object> skuAttributes;

    private String batchNo;

    /**
     * for task query to mapping
     */
    private List<Long> skuBatchStockIds;
}
