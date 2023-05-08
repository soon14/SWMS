package com.swms.wms.api.stock.dto;

import com.swms.wms.api.stock.constants.StockLockTypeEnum;
import lombok.Data;

@Data
public class SkuBatchStockLockDTO {
    private Long skuBatchStockId;
    private StockLockTypeEnum lockType;
    private Integer lockQty;
    private Long orderDetailId;
}
