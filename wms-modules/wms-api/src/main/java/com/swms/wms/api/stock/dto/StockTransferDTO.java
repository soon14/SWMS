package com.swms.wms.api.stock.dto;

import com.swms.wms.api.stock.constants.StockLockTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockTransferDTO {

    private Long skuBatchStockId;
    private Long skuBatchAttributeId;
    private Long containerStockId;
    private StockLockTypeEnum lockType;
    private Integer transferQty;
    private Long taskId;

    private String targetContainerCode;
    private String targetContainerSlotCode;

    // if stock move from one warehouse area to another area in warehouse, this field is required
    private String warehouseAreaCode;
    private String warehouseCode;

    private Long containerStockTransactionRecordId;
}
