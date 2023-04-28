package com.swms.stock.infrastructure.persistence.mapper;

import com.swms.stock.infrastructure.persistence.po.ContainerStockPO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContainerStockPORepository extends JpaRepository<ContainerStockPO, Long> {
    ContainerStockPO findBySkuBatchAttributeIdAndContainerCodeAndContainerSlotCode(Long skuBatchAttributeId, String containerCode, String containerSlotCode);
}
