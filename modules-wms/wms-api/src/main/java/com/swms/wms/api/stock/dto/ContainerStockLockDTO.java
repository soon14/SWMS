package com.swms.wms.api.stock.dto;

import com.swms.wms.api.stock.constants.StockLockTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContainerStockLockDTO {
    private Long containerStockId;
    private StockLockTypeEnum lockType;
    private Integer lockQty;
    private Long taskId;
}
