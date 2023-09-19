package com.swms.wms.stock.infrastructure.persistence.mapper;

import com.swms.wms.stock.infrastructure.persistence.po.ContainerStockPO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ContainerStockPORepository extends JpaRepository<ContainerStockPO, Long> {
    ContainerStockPO findByWarehouseCodeAndSkuBatchStockIdAndContainerCodeAndContainerSlotCode(
        String warehouseCode, Long skuBatchAttributeId, String containerCode, String containerSlotCode);

    List<ContainerStockPO> findAllByContainerCode(String containerCode);

    List<ContainerStockPO> findAllBySkuBatchStockIdIn(Collection<Long> skuBatchStockIds);
}
