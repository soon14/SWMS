package com.swms.inventory.domain.repository;

import com.swms.inventory.domain.entity.ContainerStockLock;
import com.swms.wms.api.stock.dto.StockTransferDTO;

import java.util.List;

public interface ContainerStockLockRepository {
    void saveAll(List<ContainerStockLock> containerStockLocks);

    void subtractLockStock(StockTransferDTO stockDeductDTOS);
}
