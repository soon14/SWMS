package com.swms.wms.stock.domain.service.impl;

import com.swms.wms.api.stock.dto.StockTransferDTO;
import com.swms.wms.stock.domain.entity.ContainerStock;
import com.swms.wms.stock.domain.entity.SkuBatchStock;
import com.swms.wms.stock.domain.repository.ContainerStockRepository;
import com.swms.wms.stock.domain.repository.SkuBatchStockRepository;
import com.swms.wms.stock.domain.service.StockService;
import com.swms.wms.stock.domain.transfer.SkuBatchStockTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private ContainerStockRepository containerStockRepository;

    @Autowired
    private SkuBatchStockRepository skuBatchStockRepository;

    @Autowired
    private SkuBatchStockTransfer skuBatchStockTransfer;

    @Transactional(rollbackFor = Exception.class)
    public void transferContainerStock(StockTransferDTO stockTransferDTO, ContainerStock containerStock, boolean unlock) {

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
            targetContainerStock = new ContainerStock();
            targetContainerStock.setWarehouseCode(stockTransferDTO.getWarehouseCode());
            targetContainerStock.setContainerCode(stockTransferDTO.getTargetContainerCode());
            targetContainerStock.setContainerSlotCode(stockTransferDTO.getTargetContainerSlotCode());
            targetContainerStock.setAvailableQty(stockTransferDTO.getTransferQty());
            targetContainerStock.setSkuBatchAttributeId(stockTransferDTO.getSkuBatchAttributeId());
            targetContainerStock.setTotalQty(stockTransferDTO.getTransferQty());
            targetContainerStock.setBoxStock(stockTransferDTO.isBoxStock());
            targetContainerStock.setBoxNo(stockTransferDTO.getBoxNo());
        }
        containerStockRepository.save(targetContainerStock);
    }

    @Transactional(rollbackFor = Exception.class)
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
