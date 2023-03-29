package com.swms.wms.api.stock.dto;

import com.swms.wms.api.stock.constants.StockLockTypeEnum;
import lombok.Data;

@Data
public class ContainerStockLockDTO {
    private Long stockId;
    private StockLockTypeEnum lockType;
    private Integer lockQty;
    private Long taskId;
}
