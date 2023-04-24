package com.swms.mdm.main.data.infrastructure.persistence.mapper;

import com.swms.mdm.main.data.infrastructure.persistence.po.WarehouseMainDataPO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseMainDataPORepository extends JpaRepository<WarehouseMainDataPO, Long> {
    WarehouseMainDataPO findByWarehouseCode(String warehouseCode);
}
