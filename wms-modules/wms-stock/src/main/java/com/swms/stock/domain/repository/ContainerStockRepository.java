package com.swms.stock.domain.repository;

import com.swms.stock.domain.entity.ContainerStock;
import com.swms.wms.api.stock.dto.ContainerStockLockDTO;
import com.swms.wms.api.stock.dto.StockTransferDTO;

import java.util.List;

public interface ContainerStockRepository {

    void save(ContainerStock containerStock);

    void saveAll(List<ContainerStock> toContainerStocks);

    ContainerStock findById(Long stockId);

    List<ContainerStock> findAllByIds(List<Long> containerStockIds);

    ContainerStock existsByContainerCodeAndContainerSlotCodeAndSkuBatchAttributeId(String targetContainerCode, String targetContainerSlotCode, Long skuBatchAttributeId);
}
