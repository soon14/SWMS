package com.swms.wms.api.stock.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StockCreateDTO {

    @NotEmpty
    private String warehouseCode;

    @NotNull
    private Long skuBatchAttributeId;
    @NotNull
    private Long skuId;
    @NotNull
    @Min(1)
    private Integer transferQty;
    @NotEmpty
    private String orderNo;

    // if stock move from one warehouse area to another area in warehouse, this field is required
    @NotNull
    private Long warehouseAreaId;

    // upstream LPN code or upstream inbound order
    @NotEmpty
    private String sourceContainerCode;
    private String sourceContainerSlotCode;

    @NotEmpty
    private String targetContainerCode;
    private String targetContainerSlotCode;
    private String boxNo;
    private boolean boxStock;

}
