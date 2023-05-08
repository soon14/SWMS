package com.swms.wms.task.domain.entity;

import com.google.common.base.Preconditions;
import com.swms.wms.api.basic.constants.WarehouseAreaCodeEnum;
import com.swms.wms.api.stock.constants.StockLockTypeEnum;
import com.swms.wms.api.task.constants.OperationTaskStatusEnum;
import com.swms.wms.api.task.constants.OperationTaskTypeEnum;
import lombok.Data;

import java.util.Objects;

@Data
public class OperationTask {

    private Long id;

    private String taskNo;
    private OperationTaskTypeEnum taskType;

    private String stationCode;

    private String skuCode;
    private Long skuBatchAttributeId;
    private Long skuBatchStockId;
    private Long containerStockId;

    private String sourceContainerCode;
    private String sourceContainerSlot;

    private String boxNo;

    private Integer requiredQty;
    private Integer operatedQty;
    private Integer abnormalQty;

    private String targetLocationCode;
    private String targetContainerCode;
    private String targetContainerSlotCode;

    private Long originalOrderId;
    private Long originalOrderDetailId;

    private OperationTaskStatusEnum taskStatus;


    public void validateQty() {
        Preconditions.checkState(this.requiredQty >= 0, "operated qty must be greater 0");
        Preconditions.checkState(this.operatedQty >= 0, "operated qty must be greater and equal to 0");
        Preconditions.checkState(this.abnormalQty >= 0, "abnormal qty must be greater and equal to 0");
        Preconditions.checkState(this.requiredQty >= this.operatedQty + this.abnormalQty, "abnormal qty must be greater and equal to 0");
    }

    public void operate(Integer operatedQty, Integer abnormalQty) {
        this.operatedQty += operatedQty;
        this.abnormalQty += abnormalQty;
        validateQty();
        if (Objects.equals(this.requiredQty, this.operatedQty)) {
            this.taskStatus = OperationTaskStatusEnum.PROCESSED;
        } else {
            this.taskStatus = OperationTaskStatusEnum.PROCESSING;
        }
    }

    public StockLockTypeEnum transferToLockType() {
        if (taskType == OperationTaskTypeEnum.PICKING) {
            return StockLockTypeEnum.OUTBOUND;
        } else {
            return StockLockTypeEnum.STOCK_MOVE_IN_WAREHOUSE;
        }
    }

    public String transferToWarehouseAreaCode() {
        if (taskType == OperationTaskTypeEnum.PICKING
            || taskType == OperationTaskTypeEnum.RELOCATION) {
            return WarehouseAreaCodeEnum.OFF_SHELF_TEMPORARY_STORAGE_AREA.name();
        } else {
            return WarehouseAreaCodeEnum.PUTTING_ON_THE_STAGING_AREA.name();
        }

    }
}
