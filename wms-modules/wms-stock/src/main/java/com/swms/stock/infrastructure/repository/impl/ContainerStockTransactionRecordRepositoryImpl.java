package com.swms.stock.infrastructure.repository.impl;

import com.swms.stock.domain.entity.ContainerStockTransactionRecord;
import com.swms.stock.domain.repository.ContainerStockTransactionRecordRepository;
import com.swms.stock.infrastructure.persistence.mapper.ContainerStockTransactionRecordPORepository;
import com.swms.stock.infrastructure.persistence.transfer.ContainerStockTransactionRecordPOTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContainerStockTransactionRecordRepositoryImpl implements ContainerStockTransactionRecordRepository {

    @Autowired
    private ContainerStockTransactionRecordPORepository recordMapper;

    @Autowired
    private ContainerStockTransactionRecordPOTransfer recordTransfer;

    @Override
    public void save(ContainerStockTransactionRecord transactionRecord) {
        recordMapper.save(recordTransfer.toPO(transactionRecord));
    }

    @Override
    public ContainerStockTransactionRecord findById(Long containerStockTransactionRecordId) {
        return recordTransfer.toDO(recordMapper.findById(containerStockTransactionRecordId).orElseThrow());
    }
}
