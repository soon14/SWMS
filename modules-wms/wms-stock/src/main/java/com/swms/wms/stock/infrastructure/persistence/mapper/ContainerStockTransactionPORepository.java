package com.swms.wms.stock.infrastructure.persistence.mapper;

import com.swms.wms.stock.infrastructure.persistence.po.ContainerStockTransactionPO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContainerStockTransactionPORepository extends JpaRepository<ContainerStockTransactionPO, Long> {

}
