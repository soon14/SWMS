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
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class StockTransferServiceImpl implements StockTransferService {

    @Autowired
    private SkuBatchStockRepository skuBatchStockRepository;

    @Autowired
    private ContainerStockRepository containerStockRepository;

    @Autowired
    private StockService stockManagement;

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
    public void createStock(List<StockCreateDTO> stockCreateDTOS) {

        //1. create container stock transaction
        containerStockTransactionRecordRepository.saveAll(containerStockTransactionTransfer.toDOS(stockCreateDTOS));

        //2. create container stock
        containerStockRepository.saveAll(containerStockTransfer.fromCreateDTOsToDOs(stockCreateDTOS));

        //3. create sku batch stock
        List<Long> skuBatchStockIds = stockCreateDTOS.stream().map(StockCreateDTO::getSkuBatchStockId).toList();
        List<SkuBatchStock> skuBatchStocks = skuBatchStockRepository.findAllByIds(skuBatchStockIds);

        if (CollectionUtils.isEmpty(skuBatchStocks)) {
            skuBatchStocks = skuBatchStockTransfer.fromCreateDTOsToDOs(stockCreateDTOS);
        } else {
            skuBatchStocks.forEach(skuBatchStock -> stockCreateDTOS.forEach(skuBatchStockLockDTO -> {
                if (Objects.equals(skuBatchStock.getId(), skuBatchStockLockDTO.getSkuBatchStockId())) {
                    skuBatchStock.addQty(skuBatchStockLockDTO.getTransferQty());
                }
            }));
        }
        skuBatchStockRepository.saveAll(skuBatchStocks);
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

        stockManagement.transferContainerStock(stockTransferDTO, containerStock, false);
        stockManagement.transferSkuBatchStock(stockTransferDTO, false);
    }

    @Transactional
    public void transferAndUnlockStock(StockTransferDTO stockTransferDTO, ContainerStock containerStock) {
        saveTransactionRecord(stockTransferDTO, containerStock);

        stockManagement.transferContainerStock(stockTransferDTO, containerStock, true);
        stockManagement.transferSkuBatchStock(stockTransferDTO, true);
    }

    private void saveTransactionRecord(StockTransferDTO stockTransferDTO, ContainerStock containerStock) {
        ContainerStockTransaction containerStockTransaction = containerStockTransactionTransfer.toDO(stockTransferDTO);
        containerStockTransaction.setSourceContainerCode(containerStock.getContainerCode());
        containerStockTransaction.setSourceContainerSlotCode(containerStock.getContainerSlotCode());
        containerStockTransactionRecordRepository.save(containerStockTransaction);
    }


}
