package com.swms.mdm.main.data.infrastructure.persistence.mapper;

import com.swms.mdm.main.data.infrastructure.persistence.po.WarehouseMainDataPO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface WarehouseMainDataPORepository extends JpaRepository<WarehouseMainDataPO, Long> {
    Optional<WarehouseMainDataPO> findByWarehouseCode(String warehouseCode);

    Optional<Collection<WarehouseMainDataPO>> findByWarehouseCodeIn(Collection<String> warehouseCodes);

}
