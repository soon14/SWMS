package com.swms.wms.basic.warehouse.infrastructure.persistence.mapper;

import com.swms.wms.basic.warehouse.infrastructure.persistence.po.WarehouseLogicPO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseLogicPORepository extends JpaRepository<WarehouseLogicPO, Long> {

}
