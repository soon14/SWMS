package com.swms.stock.infrastructure.repository.impl;

import com.swms.stock.domain.entity.ContainerStockTransactionRecord;
import com.swms.stock.domain.repository.ContainerStockTransactionRecordRepository;
import com.swms.stock.infrastructure.persistence.mapper.ContainerStockTransactionRecordMapper;
import com.swms.stock.infrastructure.persistence.transfer.ContainerStockTransactionRecordTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContainerStockTransactionRecordRepositoryImpl implements ContainerStockTransactionRecordRepository {

    @Autowired
    private ContainerStockTransactionRecordMapper recordMapper;

    @Autowired
    private ContainerStockTransactionRecordTransfer recordTransfer;

    @Override
    public void save(ContainerStockTransactionRecord transactionRecord) {
        recordMapper.insert(recordTransfer.toContainerTransactionRecordPO(transactionRecord));
    }

    @Override
    public ContainerStockTransactionRecord findById(Long containerStockTransactionRecordId) {
        return recordTransfer.toContainerTransactionRecord(recordMapper.selectById(containerStockTransactionRecordId));
    }
}
