package com.swms.inventory.domain.repository;

import com.swms.inventory.domain.entity.ContainerStock;
import com.swms.wms.api.stock.dto.ContainerStockLockDTO;
import com.swms.wms.api.stock.dto.StockTransferDTO;

import java.util.List;

public interface ContainerStockRepository {
    void saveAll(List<ContainerStock> toContainerStocks);

    void lockStock(List<ContainerStockLockDTO> containerStockLockDTOS);

    void subtractStock(StockTransferDTO stockDeductDTO);

    void addStock(StockTransferDTO stockDeductDTO);

    ContainerStock findById(Long stockId);
}
