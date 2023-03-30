package com.swms.stock.domain.entity;

import lombok.Data;

@Data
public class SkuBatchStockLock {

    private Long id;
    private Long skuBatchId;
    private Long orderDetailId;
    private Integer lockQty;
}
