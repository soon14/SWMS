package com.swms.stock.domain.service;

import com.google.common.collect.Lists;
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

import java.util.List;
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

    public ContainerStockTransactionRecord saveContainerStockTransactionRecord(ContainerStockTransactionRecord record) {
        return containerStockTransactionRecordRepository.save(record);
    }

    public void transferContainerStock(StockTransferDTO stockTransferDTO, boolean unlock) {

        containerStockTransactionRecordRepository.updateProcessed(stockTransferDTO.getContainerStockTransactionRecordId());

        ContainerStock containerStock = containerStockRepository.findById(stockTransferDTO.getContainerStockId());
        if (Objects.equals(containerStock.getContainerCode(), stockTransferDTO.getTargetContainerCode())) {
            return;
        }

        if (unlock) {
            containerStockRepository.subtractAndUnlockStock(stockTransferDTO);
        } else {
            containerStockRepository.subtractStock(stockTransferDTO);
        }

        //need add or update container stock
        ContainerStock targetContainerStock = containerStockRepository.existsByContainerCodeAndContainerSlotCodeAndSkuBatchAttributeId(
            stockTransferDTO.getTargetContainerCode(), stockTransferDTO.getTargetContainerSlotCode(),
            stockTransferDTO.getSkuBatchAttributeId());
        if (targetContainerStock != null) {
            containerStockRepository.addTargetContainerStock(stockTransferDTO);
        } else {
            List<ContainerStock> containerStocks = Lists.newArrayList(ContainerStock.builder()
                .containerCode(stockTransferDTO.getTargetContainerCode())
                .containerSlotCode(stockTransferDTO.getTargetContainerSlotCode())
                .availableQty(stockTransferDTO.getTransferQty())
                .skuBatchAttributeId(stockTransferDTO.getSkuBatchAttributeId())
                .totalQty(stockTransferDTO.getTransferQty()).build());
            containerStockRepository.saveAll(containerStocks);
        }
    }

    public void transferSkuBatchStock(StockTransferDTO stockTransferDTO, boolean unlock) {
        if (unlock) {
            skuBatchStockRepository.subtractAndUnlockStock(stockTransferDTO);
        } else {
            skuBatchStockRepository.subtractStock(stockTransferDTO);
        }

        SkuBatchStock skuBatchStock = skuBatchStockRepository.findBySkuBatchAttributeIdAndWarehouseAreaCode(
            stockTransferDTO.getSkuBatchAttributeId(), stockTransferDTO.getWarehouseAreaCode());
        if (skuBatchStock == null) {
            skuBatchStockRepository.saveAll(skuBatchStockTransfer.toSkuBatchStocks(Lists.newArrayList(stockTransferDTO)));
        } else {
            stockTransferDTO.setSkuBatchStockId(skuBatchStock.getId());
            skuBatchStockRepository.addStock(Lists.newArrayList(stockTransferDTO));
        }
    }
}
