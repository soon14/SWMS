package com.swms.wms.stock.domain.entity;

import com.google.common.base.Preconditions;
import com.swms.common.utils.base.UpdateUserPO;
import com.swms.wms.api.stock.constants.StockLockTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * stock design rule:
 * 1. stock created by receiving;
 * 2. stock transfer from one area to another area in warehouse;
 * 3. stock subtraction by shipping. if our system don't contain shipping module, then scheduled delete shipping area stock;
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SkuBatchStock extends UpdateUserPO {

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

    public void setWarehouseAreaCode(Long warehouseAreaId) {
        Preconditions.checkState(warehouseAreaId != null && warehouseAreaId > 0, "warehouseAreaCode cannot be empty");
        this.warehouseAreaId = warehouseAreaId;
    }

    public void validateQty() {
        Preconditions.checkState(this.availableQty >= 0, "available qty must be greater than 0");
        Preconditions.checkState(this.totalQty >= 0, "total qty must be greater than 0");
        Preconditions.checkState(this.outboundLockedQty >= 0, "outbound lock qty must be greater than 0");
        Preconditions.checkState(this.noOutboundLockedQty >= 0, "no outbound lock qty must be greater than 0");
        Preconditions.checkState(this.totalQty == this.availableQty + this.outboundLockedQty + this.noOutboundLockedQty,
            "total qty must equals availableQty + noOutboundLockedQty + outboundLockedQty");
    }

    public void lockQty(Integer lockQty, StockLockTypeEnum stockLockType) {
        this.availableQty -= lockQty;
        if (stockLockType == StockLockTypeEnum.OUTBOUND) {
            this.outboundLockedQty += lockQty;
        } else {
            this.noOutboundLockedQty += lockQty;
        }
        validateQty();
    }

    public void addQty(Integer addQty) {
        this.totalQty += addQty;
        this.availableQty += addQty;
        validateQty();
    }

    public void addAndLockQty(Integer addQty, StockLockTypeEnum stockLockType) {
        this.totalQty += addQty;
        if (stockLockType == StockLockTypeEnum.OUTBOUND) {
            this.outboundLockedQty += addQty;
        } else {
            this.noOutboundLockedQty += addQty;
        }
        validateQty();
    }

    public void subtractQty(Integer subtractQty) {
        this.totalQty -= subtractQty;
        this.availableQty -= subtractQty;
        validateQty();
    }

    public void subtractAndUnlockQty(Integer subtractQty, StockLockTypeEnum stockLockType) {
        this.totalQty -= subtractQty;
        if (stockLockType == StockLockTypeEnum.OUTBOUND) {
            this.outboundLockedQty -= subtractQty;
        } else {
            this.noOutboundLockedQty -= subtractQty;
        }
        validateQty();
    }
}
