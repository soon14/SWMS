package com.swms.wms.stock.domain.service.impl;

import com.swms.wms.api.stock.dto.StockCreateDTO;
import com.swms.wms.api.stock.dto.StockTransferDTO;
import com.swms.wms.stock.domain.entity.ContainerStock;
import com.swms.wms.stock.domain.entity.ContainerStockTransaction;
import com.swms.wms.stock.domain.entity.SkuBatchStock;
import com.swms.wms.stock.domain.repository.ContainerStockRepository;
import com.swms.wms.stock.domain.repository.ContainerStockTransactionRepository;
import com.swms.wms.stock.domain.repository.SkuBatchStockRepository;
import com.swms.wms.stock.domain.service.StockService;
import com.swms.wms.stock.domain.service.StockTransferService;
import com.swms.wms.stock.domain.transfer.ContainerStockTransactionTransfer;
import com.swms.wms.stock.domain.transfer.ContainerStockTransfer;
import com.swms.wms.stock.domain.transfer.SkuBatchStockTransfer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Slf4j
@Validated
public class StockTransferServiceImpl implements StockTransferService {

    @Autowired
    private SkuBatchStockRepository skuBatchStockRepository;

    @Autowired
    private ContainerStockRepository containerStockRepository;

    @Autowired
    private StockService stockService;

    @Autowired
    private ContainerStockTransfer containerStockTransfer;

    @Autowired
    private SkuBatchStockTransfer skuBatchStockTransfer;

    @Autowired
    private ContainerStockTransactionTransfer containerStockTransactionTransfer;

    @Autowired
    private ContainerStockTransactionRepository containerStockTransactionRecordRepository;

    /**
     * when stock is moved from outside to warehouse, then stock is created. e.g: receiving
     *
     * @param stockCreateDTOS
     */
    @Transactional
    public void createStock(StockCreateDTO stockCreateDTO) {

        //1. create sku batch stock
        SkuBatchStock skuBatchStock = skuBatchStockRepository
            .findBySkuBatchAttributeIdAndWarehouseAreaId(stockCreateDTO.getSkuBatchAttributeId(), stockCreateDTO.getWarehouseAreaId());
        if (skuBatchStock == null) {
            skuBatchStock = skuBatchStockTransfer.fromCreateDTOtoDO(stockCreateDTO);
        } else {
            skuBatchStock.addQty(stockCreateDTO.getTransferQty());
        }
        SkuBatchStock savedSkuBatchStock = skuBatchStockRepository.save(skuBatchStock);

        //2. create container stock transaction
        containerStockTransactionRecordRepository.save(containerStockTransactionTransfer.fromCreateDTOtoDO(stockCreateDTO, savedSkuBatchStock.getId()));

        //3. create container stock
        ContainerStock containerStock = containerStockTransfer.fromCreateDTOtoDO(stockCreateDTO, savedSkuBatchStock.getId());
        containerStockRepository.save(containerStock);
    }

    /**
     * when stock is move from on area to another area in warehouse, then stock is transferred. e.g: picking, putAway
     * <p>attention: call this function before stock is locked </p>
     *
     * @param stockTransferDTO
     * @param containerStock
     */
    @Transactional
    public void transferStock(StockTransferDTO stockTransferDTO, ContainerStock containerStock) {
        saveTransactionRecord(stockTransferDTO, containerStock);

        Long targetSkuBatchId = stockService.transferSkuBatchStock(stockTransferDTO, false);
        stockService.transferContainerStock(stockTransferDTO, containerStock, targetSkuBatchId, false);
    }

    @Transactional
    public void transferAndUnlockStock(StockTransferDTO stockTransferDTO, ContainerStock containerStock) {
        saveTransactionRecord(stockTransferDTO, containerStock);

        Long targetSkuBatchId = stockService.transferSkuBatchStock(stockTransferDTO, true);
        stockService.transferContainerStock(stockTransferDTO, containerStock, targetSkuBatchId, true);
    }

    private void saveTransactionRecord(StockTransferDTO stockTransferDTO, ContainerStock containerStock) {
        ContainerStockTransaction containerStockTransaction = containerStockTransactionTransfer.toDO(stockTransferDTO);
        containerStockTransaction.setSourceContainerCode(containerStock.getContainerCode());
        containerStockTransaction.setSourceContainerSlotCode(containerStock.getContainerSlotCode());
        containerStockTransactionRecordRepository.save(containerStockTransaction);
    }
}
