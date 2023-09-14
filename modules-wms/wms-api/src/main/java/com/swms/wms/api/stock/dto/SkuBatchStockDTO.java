package com.swms.wms.api.stock.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SkuBatchStockDTO {

    private Long id;

    private Long skuId;

    //unique key union skuBatchAttributeId and warehouseAreaCode and warehouseCode
    private String warehouseCode;
    private Long skuBatchAttributeId;
    private Long warehouseAreaId;

    private Integer totalQty;
    private Integer availableQty;
    // outbound locked qty
    private Integer outboundLockedQty;
    // other operation locked qty in the warehouse
    private Integer noOutboundLockedQty;
    private Long version;
}
