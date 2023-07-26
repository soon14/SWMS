package com.swms.wms.api.stock.dto;

import com.swms.wms.api.stock.constants.StockLockTypeEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockTransferDTO {

    @NotEmpty
    private String warehouseCode;

    @NotNull
    private Long skuBatchStockId;
    @NotNull
    private Long skuBatchAttributeId;
    @NotNull
    private Long skuId;
    private Long containerStockId;
    private StockLockTypeEnum lockType;
    @NotNull
    @Min(1)
    private Integer transferQty;
    @NotNull
    private Long taskId;
    @NotNull
    private String orderNo;

    @NotNull
    private String targetContainerCode;
    private String targetContainerSlotCode;
    private String boxNo;
    private boolean boxStock;

    // if stock move from one warehouse area to another area in warehouse, this field is required
    @NotNull
    private Long warehouseAreaId;

    @NotNull
    private Long containerStockTransactionRecordId;
}
