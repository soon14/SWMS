package com.swms.wms.basic.warehouse.infrastructure.persistence.mapper;

import com.swms.wms.basic.warehouse.infrastructure.persistence.po.WarehouseAreaPO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WarehouseAreaPORepository extends JpaRepository<WarehouseAreaPO, Long> {

    List<WarehouseAreaPO> findByWarehouseGroupCodeAndWarehouseCode(String warehouseGroupCode, String warehouseCode);
}
