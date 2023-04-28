package com.swms.stock.infrastructure.persistence.mapper;

import com.swms.stock.infrastructure.persistence.po.ContainerStockTransactionRecordPO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContainerStockTransactionRecordPORepository extends JpaRepository<ContainerStockTransactionRecordPO, Long> {
}
