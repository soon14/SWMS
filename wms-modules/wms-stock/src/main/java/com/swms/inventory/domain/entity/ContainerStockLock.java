package com.swms.inventory.domain.entity;

import lombok.Data;

@Data
public class ContainerStockLock {

    private Long id;
    private Long skuBatchId;
    private Long taskId;
    private Integer lockQty;
}
