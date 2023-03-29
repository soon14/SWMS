package com.swms.wms.api.stock.dto;

import com.swms.wms.api.stock.constants.StockLockTypeEnum;
import lombok.Data;

@Data
public class StockTransferDTO {

    private Long skuBatchId;
    private Long skuBatchAttributeId;
    private Long stockId;
    private StockLockTypeEnum lockType;
    private Integer transferQty;
    private Long taskId;
    private Long orderDetailId;

    private Long targetContainerId;
    private String targetContainerCode;
    private Long targetContainerSlotId;
    private String targetContainerSlot;

    // if stock move from one warehouse area to another area in warehouse, this field is required
    private String warehouseAreaCode;
    private String warehouseCode;
}
