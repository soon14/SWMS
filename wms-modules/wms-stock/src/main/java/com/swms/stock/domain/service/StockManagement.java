package com.swms.stock.domain.service;

import com.swms.stock.domain.entity.ContainerStock;
import com.swms.stock.domain.entity.ContainerStockTransactionRecord;
import com.swms.stock.domain.entity.SkuBatchStock;
import com.swms.stock.domain.repository.ContainerStockRepository;
import com.swms.stock.domain.repository.ContainerStockTransactionRecordRepository;
import com.swms.stock.domain.repository.SkuBatchStockRepository;
import com.swms.stock.domain.transfer.SkuBatchStockTransfer;
import com.swms.wms.api.stock.dto.StockTransferDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class StockManagement {

    @Autowired
    private ContainerStockRepository containerStockRepository;

    @Autowired
    private SkuBatchStockRepository skuBatchStockRepository;

    @Autowired
    private SkuBatchStockTransfer skuBatchStockTransfer;

    @Autowired
    private ContainerStockTransactionRecordRepository containerStockTransactionRecordRepository;

    public void saveContainerStockTransactionRecord(ContainerStockTransactionRecord record) {
        containerStockTransactionRecordRepository.save(record);
    }

    public void transferContainerStock(StockTransferDTO stockTransferDTO, boolean unlock) {

        ContainerStockTransactionRecord transactionRecord = containerStockTransactionRecordRepository
            .findById(stockTransferDTO.getContainerStockTransactionRecordId());
        transactionRecord.setProcessed(true);
        containerStockTransactionRecordRepository.save(transactionRecord);

        ContainerStock containerStock = containerStockRepository.findById(stockTransferDTO.getContainerStockId());
        if (Objects.equals(containerStock.getContainerCode(), stockTransferDTO.getTargetContainerCode())) {
            containerStock.setWarehouseAreaCode(stockTransferDTO.getWarehouseAreaCode());
            containerStockRepository.save(containerStock);
            return;
        }

        if (unlock) {
            containerStock.subtractAndUnlockQty(stockTransferDTO.getTransferQty(), stockTransferDTO.getLockType());
        } else {
            containerStock.subtractQty(stockTransferDTO.getTransferQty());
        }
        containerStockRepository.save(containerStock);

        //need add or update container stock
        ContainerStock targetContainerStock = containerStockRepository.findByContainerAndSlotAndBatchAttribute(
            stockTransferDTO.getTargetContainerCode(), stockTransferDTO.getTargetContainerSlotCode(),
            stockTransferDTO.getSkuBatchAttributeId());
        if (targetContainerStock != null) {
            targetContainerStock.addQty(stockTransferDTO.getTransferQty());
        } else {
            targetContainerStock = ContainerStock.builder()
                .containerCode(stockTransferDTO.getTargetContainerCode())
                .containerSlotCode(stockTransferDTO.getTargetContainerSlotCode())
                .availableQty(stockTransferDTO.getTransferQty())
                .skuBatchAttributeId(stockTransferDTO.getSkuBatchAttributeId())
                .warehouseAreaCode(stockTransferDTO.getWarehouseAreaCode())
                .totalQty(stockTransferDTO.getTransferQty()).build();
        }
        containerStockRepository.save(targetContainerStock);
    }

    public void transferSkuBatchStock(StockTransferDTO stockTransferDTO, boolean unlock) {

        SkuBatchStock skuBatchStock = skuBatchStockRepository.findById(stockTransferDTO.getSkuBatchStockId());
        if (unlock) {
            skuBatchStock.subtractAndUnlockQty(stockTransferDTO.getTransferQty(), stockTransferDTO.getLockType());
        } else {
            skuBatchStock.subtractQty(stockTransferDTO.getTransferQty());
        }
        skuBatchStockRepository.save(skuBatchStock);

        SkuBatchStock targetSkuBatch = skuBatchStockRepository.findBySkuBatchAttributeIdAndWarehouseAreaCode(
            stockTransferDTO.getSkuBatchAttributeId(), stockTransferDTO.getWarehouseAreaCode());
        if (targetSkuBatch == null) {
            targetSkuBatch = skuBatchStockTransfer.toSkuBatchStock(stockTransferDTO);
        } else {
            targetSkuBatch.addQty(stockTransferDTO.getTransferQty());
        }
        skuBatchStockRepository.save(targetSkuBatch);
    }
}
