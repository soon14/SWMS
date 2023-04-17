package com.swms.stock.domain.repository;

import com.swms.stock.domain.entity.ContainerStockTransactionRecord;

public interface ContainerStockTransactionRecordRepository {

    void save(ContainerStockTransactionRecord transactionRecord);

    ContainerStockTransactionRecord findById(Long containerStockTransactionRecordId);
}
