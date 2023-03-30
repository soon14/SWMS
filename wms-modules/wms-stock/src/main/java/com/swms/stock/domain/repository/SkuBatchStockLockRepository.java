package com.swms.stock.domain.repository;

import com.swms.stock.domain.entity.SkuBatchStockLock;
import com.swms.wms.api.stock.dto.StockTransferDTO;

import java.util.List;

public interface SkuBatchStockLockRepository {
    void saveAll(List<SkuBatchStockLock> skuBatchStockLocks);

    void subtractLockStock(StockTransferDTO stockDeductDTOS);
}
