package com.swms.wms.stock.infrastructure.repository.impl;

import com.swms.wms.stock.domain.entity.ContainerStockTransaction;
import com.swms.wms.stock.domain.repository.ContainerStockTransactionRepository;
import com.swms.wms.stock.infrastructure.persistence.mapper.ContainerStockTransactionPORepository;
import com.swms.wms.stock.infrastructure.persistence.transfer.ContainerStockTransactionPOTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContainerStockTransactionRepositoryImpl implements ContainerStockTransactionRepository {

    @Autowired
    private ContainerStockTransactionPORepository recordPORepository;

    @Autowired
    private ContainerStockTransactionPOTransfer recordPOTransfer;

    @Override
    public ContainerStockTransaction save(ContainerStockTransaction transactionRecord) {
        return recordPOTransfer.toDO(recordPORepository.save(recordPOTransfer.toPO(transactionRecord)));
    }

    @Override
    public ContainerStockTransaction findById(Long containerStockTransactionRecordId) {
        return recordPOTransfer.toDO(recordPORepository.findById(containerStockTransactionRecordId).orElseThrow());
    }

    @Override
    public void saveAll(List<ContainerStockTransaction> containerStockTransactions) {
        recordPORepository.saveAll(recordPOTransfer.toPOS(containerStockTransactions));
    }
}
