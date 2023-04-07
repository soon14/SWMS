package com.swms.stock.domain.repository;

import com.swms.stock.domain.entity.ContainerStockTransactionRecord;

public interface ContainerStockTransactionRecordRepository {

    ContainerStockTransactionRecord save(ContainerStockTransactionRecord record);

    void updateProcessed(Long id);
}
