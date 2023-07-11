package com.swms.wms.basic.warehouse.infrastructure.persistence.mapper;

import com.swms.wms.basic.warehouse.infrastructure.persistence.po.WarehouseAreaGroupPO;
import com.swms.wms.basic.warehouse.infrastructure.persistence.po.WarehouseAreaPO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseAreaGroupPORepository extends JpaRepository<WarehouseAreaGroupPO, Long> {

}
