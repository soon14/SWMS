package com.swms.wms.stock.domain.entity;

import com.google.common.base.Preconditions;
import com.swms.common.utils.base.UpdateUserDTO;
import com.swms.wms.api.stock.constants.StockLockTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ContainerStock extends UpdateUserDTO {

    private Long id;

    private Long skuBatchStockId;

    /**
     * container is not must be a physical container. e.g.
     * when sku received to a place but not a physical container,
     * then the container code will be received order no ,
     * and when sku put away on the rack, then the container code is the location code;
     */
    private String warehouseCode;
    private String containerCode;
    private String containerSlotCode;

    private Integer totalQty;
    private Integer availableQty;
    // outbound locked qty
    private Integer outboundLockedQty;
    // other operation locked qty in the warehouse
    private Integer noOutboundLockedQty;
    private Integer frozenQty;

    /**
     * it means the container is or not a physical container
     */
    private boolean boxStock;
    private String boxNo;

    private Long version;

    public void validateQty() {
        Preconditions.checkState(this.availableQty >= 0, "available qty must be greater than 0");
        Preconditions.checkState(this.totalQty >= 0, "total qty must be greater than 0");
        Preconditions.checkState(this.outboundLockedQty >= 0, "outbound lock qty must be greater than 0");
        Preconditions.checkState(this.noOutboundLockedQty >= 0, "no outbound lock qty must be greater than 0");
        Preconditions.checkState(this.totalQty == this.availableQty + this.outboundLockedQty
                + this.noOutboundLockedQty + this.frozenQty,
            "total qty must equals availableQty + noOutboundLockedQty + outboundLockedQty + frozenQty");
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

    public void freezeQty(Integer freezeQty) {
        this.frozenQty += freezeQty;
        this.availableQty -= freezeQty;
        validateQty();
    }

    public void unFreezeQty(Integer unFreezeQty) {
        this.frozenQty -= unFreezeQty;
        this.availableQty += unFreezeQty;
        validateQty();
    }
}
