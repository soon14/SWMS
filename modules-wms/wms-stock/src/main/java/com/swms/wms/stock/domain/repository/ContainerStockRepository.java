package com.swms.wms.stock.domain.repository;

import com.swms.wms.stock.domain.entity.ContainerStock;

import java.util.List;

public interface ContainerStockRepository {

    void save(ContainerStock containerStock);

    void saveAll(List<ContainerStock> toContainerStocks);

    ContainerStock findById(Long stockId);

    List<ContainerStock> findAllByIds(List<Long> containerStockIds);

    ContainerStock findByContainerAndSlotAndSkuBatch(String targetContainerCode, String targetContainerSlotCode,
                                                     String warehouseCode, Long skuBatchStockId);

    List<ContainerStock> findAllByContainerCode(String containerCode);
}
