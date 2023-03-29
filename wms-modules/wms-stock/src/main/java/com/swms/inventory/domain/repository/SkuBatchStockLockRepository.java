package com.swms.inventory.domain.repository;

import com.swms.inventory.domain.entity.SkuBatchStockLock;
import com.swms.wms.api.stock.dto.StockTransferDTO;

import java.util.List;

public interface SkuBatchStockLockRepository {
    void saveAll(List<SkuBatchStockLock> skuBatchStockLocks);

    void subtractLockStock(StockTransferDTO stockDeductDTOS);
}
