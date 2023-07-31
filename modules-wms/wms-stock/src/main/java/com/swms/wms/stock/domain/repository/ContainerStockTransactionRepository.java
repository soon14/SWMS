package com.swms.wms.stock.domain.repository;

import com.swms.wms.stock.domain.entity.ContainerStockTransaction;

import java.util.List;

public interface ContainerStockTransactionRepository {

    ContainerStockTransaction save(ContainerStockTransaction transactionRecord);

    ContainerStockTransaction findById(Long containerStockTransactionRecordId);

    void saveAll(List<ContainerStockTransaction> containerStockTransactions);
}
