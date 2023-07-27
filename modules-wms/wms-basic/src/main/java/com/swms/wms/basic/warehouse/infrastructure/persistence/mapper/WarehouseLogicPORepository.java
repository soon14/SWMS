package com.swms.wms.basic.warehouse.infrastructure.persistence.mapper;

import com.swms.wms.basic.warehouse.infrastructure.persistence.po.WarehouseLogicPO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface WarehouseLogicPORepository extends JpaRepository<WarehouseLogicPO, Long> {

    List<WarehouseLogicPO> findByWarehouseAreaIdIn(Collection<Long> warehouseAreaIds);
}
