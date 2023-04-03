package com.swms.stock.domain.repository;

import com.swms.stock.domain.entity.ContainerStock;
import com.swms.wms.api.stock.dto.ContainerStockLockDTO;
import com.swms.wms.api.stock.dto.StockTransferDTO;

import java.util.List;

public interface ContainerStockRepository {
    void saveAll(List<ContainerStock> toContainerStocks);

    void lockStock(List<ContainerStockLockDTO> containerStockLockDTOS);

    void subtractStock(StockTransferDTO stockTransferDTO);

    ContainerStock findById(Long stockId);

    ContainerStock existsByContainerCodeAndContainerSlotCodeAndSkuBatchAttributeId(String targetContainerCode, String targetContainerSlotCode, Long skuBatchAttributeId);

    void addTargetContainerStock(StockTransferDTO stockTransferDTO);
}
