package com.swms.stock.infrastructure.persistence.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "sku_batch_stock")
public class SkuBatchStockPO {
    private Long id;

    private Long skuId;

    //unique key union skuBatchAttributeId and warehouseAreaCode
    private Long skuBatchAttributeId;
    private String warehouseAreaCode;

    private String warehouseCode;

    private Integer totalQty;
    private Integer availableQty;
    // outbound locked qty
    private Integer outboundLockedQty;
    // other operation locked qty in the warehouse
    private Integer noOutboundLockedQty;
    private Long version;
}
