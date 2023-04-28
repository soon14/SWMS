package com.swms.wms.stock.infrastructure.persistence.mapper;

import com.swms.wms.stock.domain.entity.SkuBatchAttribute;
import com.swms.wms.stock.infrastructure.persistence.po.SkuBatchAttributePO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkuBatchAttributePORepository extends JpaRepository<SkuBatchAttributePO, Long> {

    List<SkuBatchAttributePO> findAllBySkuId(Long skuId);
}
