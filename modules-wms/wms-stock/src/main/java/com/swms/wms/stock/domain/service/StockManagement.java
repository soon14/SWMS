package com.swms.wms.stock.domain.service;

import com.swms.wms.api.stock.dto.StockTransferDTO;
import com.swms.wms.stock.domain.entity.ContainerStock;
import com.swms.wms.stock.domain.entity.SkuBatchStock;
import com.swms.wms.stock.domain.repository.ContainerStockRepository;
import com.swms.wms.stock.domain.repository.SkuBatchStockRepository;
import com.swms.wms.stock.domain.transfer.SkuBatchStockTransfer;
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

    public void transferContainerStock(StockTransferDTO stockTransferDTO, boolean unlock) {

        ContainerStock containerStock = containerStockRepository.findById(stockTransferDTO.getContainerStockId());
        if (Objects.equals(stockTransferDTO.getWarehouseCode(), containerStock.getWarehouseCode())
            && Objects.equals(containerStock.getContainerCode(), stockTransferDTO.getTargetContainerCode())) {
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
                .warehouseCode(stockTransferDTO.getWarehouseCode())
                .containerCode(stockTransferDTO.getTargetContainerCode())
                .containerSlotCode(stockTransferDTO.getTargetContainerSlotCode())
                .availableQty(stockTransferDTO.getTransferQty())
                .skuBatchAttributeId(stockTransferDTO.getSkuBatchAttributeId())
                .totalQty(stockTransferDTO.getTransferQty())
                .boxStock(stockTransferDTO.isBoxStock())
                .boxNo(stockTransferDTO.getBoxNo()).build();
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
            stockTransferDTO.getSkuBatchAttributeId(), stockTransferDTO.getWarehouseAreaId());
        if (targetSkuBatch == null) {
            targetSkuBatch = skuBatchStockTransfer.toDO(stockTransferDTO);
        } else {
            targetSkuBatch.addQty(stockTransferDTO.getTransferQty());
        }
        skuBatchStockRepository.save(targetSkuBatch);
    }
}
