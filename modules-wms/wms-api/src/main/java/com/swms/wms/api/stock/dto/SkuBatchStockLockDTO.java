package com.swms.wms.api.stock.dto;

import com.swms.wms.api.stock.constants.StockLockTypeEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SkuBatchStockLockDTO {

    @NotNull
    private Long skuBatchStockId;
    @NotNull
    private StockLockTypeEnum lockType;
    @NotNull
    private Integer lockQty;
    private Long orderDetailId;
}
