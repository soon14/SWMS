package com.swms.stock.infrastructure.persistence.mapper;

import com.swms.stock.infrastructure.persistence.po.SkuBatchStockPO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkuBatchStockPORepository extends JpaRepository<SkuBatchStockPO, Long> {
    List<SkuBatchStockPO> findAllBySkuBatchAttributeId(Long skuBatchAttributeId);

    SkuBatchStockPO findBySkuBatchAttributeIdAndWarehouseAreaCode(Long skuBatchAttributeId, String warehouseAreaCode);
}
