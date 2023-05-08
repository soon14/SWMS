package com.swms.wms.stock.domain.repository;

import com.swms.wms.stock.domain.entity.ContainerStockTransactionRecord;

public interface ContainerStockTransactionRecordRepository {

    ContainerStockTransactionRecord save(ContainerStockTransactionRecord transactionRecord);

    ContainerStockTransactionRecord findById(Long containerStockTransactionRecordId);
}
