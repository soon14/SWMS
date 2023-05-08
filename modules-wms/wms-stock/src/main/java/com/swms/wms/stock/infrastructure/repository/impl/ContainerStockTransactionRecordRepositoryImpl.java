package com.swms.wms.stock.infrastructure.repository.impl;

import com.swms.wms.stock.domain.entity.ContainerStockTransactionRecord;
import com.swms.wms.stock.domain.repository.ContainerStockTransactionRecordRepository;
import com.swms.wms.stock.infrastructure.persistence.mapper.ContainerStockTransactionRecordPORepository;
import com.swms.wms.stock.infrastructure.persistence.transfer.ContainerStockTransactionRecordPOTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContainerStockTransactionRecordRepositoryImpl implements ContainerStockTransactionRecordRepository {

    @Autowired
    private ContainerStockTransactionRecordPORepository recordPORepository;

    @Autowired
    private ContainerStockTransactionRecordPOTransfer recordPOTransfer;

    @Override
    public ContainerStockTransactionRecord save(ContainerStockTransactionRecord transactionRecord) {
        return recordPOTransfer.toDO(recordPORepository.save(recordPOTransfer.toPO(transactionRecord)));
    }

    @Override
    public ContainerStockTransactionRecord findById(Long containerStockTransactionRecordId) {
        return recordPOTransfer.toDO(recordPORepository.findById(containerStockTransactionRecordId).orElseThrow());
    }
}
