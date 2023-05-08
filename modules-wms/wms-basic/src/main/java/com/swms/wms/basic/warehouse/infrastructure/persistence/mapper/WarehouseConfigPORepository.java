package com.swms.wms.basic.warehouse.infrastructure.persistence.mapper;

import com.swms.wms.basic.warehouse.infrastructure.persistence.po.WarehouseConfigPO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseConfigPORepository extends JpaRepository<WarehouseConfigPO, Long> {

    WarehouseConfigPO findByWarehouseCode(String warehouseCode);
}
