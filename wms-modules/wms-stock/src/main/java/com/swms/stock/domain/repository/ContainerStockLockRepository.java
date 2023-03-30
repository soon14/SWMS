package com.swms.stock.domain.repository;

import com.swms.stock.domain.entity.ContainerStockLock;
import com.swms.wms.api.stock.dto.StockTransferDTO;

import java.util.List;

public interface ContainerStockLockRepository {
    void saveAll(List<ContainerStockLock> containerStockLocks);

    int subtractLockStock(StockTransferDTO stockDeductDTO);
}
