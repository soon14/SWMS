package com.swms.wms.stock.domain.service.impl;

import com.swms.wms.api.stock.dto.StockTransferDTO;
import com.swms.wms.stock.domain.entity.ContainerStock;
import com.swms.wms.stock.domain.entity.SkuBatchStock;
import com.swms.wms.stock.domain.repository.ContainerStockRepository;
import com.swms.wms.stock.domain.repository.SkuBatchStockRepository;
import com.swms.wms.stock.domain.service.StockService;
import com.swms.wms.stock.domain.transfer.ContainerStockTransfer;
import com.swms.wms.stock.domain.transfer.SkuBatchStockTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private ContainerStockRepository containerStockRepository;

    @Autowired
    private SkuBatchStockRepository skuBatchStockRepository;

    @Autowired
    private SkuBatchStockTransfer skuBatchStockTransfer;

    @Autowired
    private ContainerStockTransfer containerStockTransfer;

    @Transactional(rollbackFor = Exception.class)
    public void transferContainerStock(StockTransferDTO stockTransferDTO, ContainerStock containerStock, Long targetSkuBatchId, boolean unlock) {

        if (unlock) {
            containerStock.subtractAndUnlockQty(stockTransferDTO.getTransferQty(), stockTransferDTO.getLockType());
        } else {
            containerStock.subtractQty(stockTransferDTO.getTransferQty());
        }
        containerStockRepository.save(containerStock);

        //need add or update container stock
        ContainerStock targetContainerStock = containerStockRepository.findByContainerAndSlotAndSkuBatch(
            stockTransferDTO.getTargetContainerCode(), stockTransferDTO.getTargetContainerSlotCode(),
            stockTransferDTO.getWarehouseCode(), stockTransferDTO.getSkuBatchStockId());
        if (targetContainerStock != null) {
            targetContainerStock.addQty(stockTransferDTO.getTransferQty());
        } else {
            targetContainerStock = containerStockTransfer.toDO(stockTransferDTO, targetSkuBatchId);
        }
        containerStockRepository.save(targetContainerStock);
    }

    @Transactional(rollbackFor = Exception.class)
    public Long transferSkuBatchStock(StockTransferDTO stockTransferDTO, boolean unlock) {

        SkuBatchStock skuBatchStock = skuBatchStockRepository.findById(stockTransferDTO.getSkuBatchStockId());

        if (Objects.equals(stockTransferDTO.getWarehouseAreaId(), skuBatchStock.getWarehouseAreaId())) {
            return skuBatchStock.getId();
        }
        if (unlock) {
            skuBatchStock.subtractAndUnlockQty(stockTransferDTO.getTransferQty(), stockTransferDTO.getLockType());
        } else {
            skuBatchStock.subtractQty(stockTransferDTO.getTransferQty());
        }
        skuBatchStockRepository.save(skuBatchStock);

        SkuBatchStock targetSkuBatch = skuBatchStockRepository.findBySkuBatchAttributeIdAndWarehouseAreaId(
            skuBatchStock.getSkuBatchAttributeId(), stockTransferDTO.getWarehouseAreaId());
        if (targetSkuBatch == null) {
            targetSkuBatch = skuBatchStockTransfer.toDO(stockTransferDTO, skuBatchStock.getSkuBatchAttributeId());
        } else {
            targetSkuBatch.addQty(stockTransferDTO.getTransferQty());
        }

        return skuBatchStockRepository.save(targetSkuBatch).getId();
    }

}
