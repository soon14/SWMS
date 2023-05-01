package com.swms.wms.stock.domain.service.impl;

import com.swms.wms.api.stock.dto.StockTransferDTO;
import com.swms.wms.stock.domain.entity.ContainerStock;
import com.swms.wms.stock.domain.entity.ContainerStockTransactionRecord;
import com.swms.wms.stock.domain.entity.SkuBatchStock;
import com.swms.wms.stock.domain.repository.ContainerStockRepository;
import com.swms.wms.stock.domain.repository.ContainerStockTransactionRecordRepository;
import com.swms.wms.stock.domain.repository.SkuBatchStockRepository;
import com.swms.wms.stock.domain.service.StockManagement;
import com.swms.wms.stock.domain.service.StockTransferService;
import com.swms.wms.stock.domain.transfer.ContainerStockTransactionRecordTransfer;
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
    private StockManagement stockManagement;

    @Autowired
    private ContainerStockTransfer containerStockTransfer;

    @Autowired
    private SkuBatchStockTransfer skuBatchStockTransfer;

    @Autowired
    private ContainerStockTransactionRecordTransfer containerStockTransactionRecordTransfer;

    @Autowired
    private ContainerStockTransactionRecordRepository containerStockTransactionRecordRepository;

    /**
     * when stock is moved from outside to warehouse, then stock is created. e.g: receiving
     *
     * @param stockTransferDTOS
     */
    @Transactional
    public void createStock(List<StockTransferDTO> stockTransferDTOS) {
        containerStockRepository.saveAll(containerStockTransfer.toDOs(stockTransferDTOS));

        List<Long> skuBatchStockIds = stockTransferDTOS.stream().map(StockTransferDTO::getSkuBatchStockId).toList();
        List<SkuBatchStock> skuBatchStocks = skuBatchStockRepository.findAllByIds(skuBatchStockIds);

        if (CollectionUtils.isEmpty(skuBatchStocks)) {
            skuBatchStocks = skuBatchStockTransfer.toDOs(stockTransferDTOS);
        } else {
            skuBatchStocks.forEach(skuBatchStock -> stockTransferDTOS.forEach(skuBatchStockLockDTO -> {
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
     */
    @Transactional
    public void transferStock(StockTransferDTO stockTransferDTO) {
        saveTransactionRecord(stockTransferDTO);

        stockManagement.transferContainerStock(stockTransferDTO, false);
        stockManagement.transferSkuBatchStock(stockTransferDTO, false);
    }

    @Transactional
    public void transferAndUnlockStock(StockTransferDTO stockTransferDTO) {
        saveTransactionRecord(stockTransferDTO);

        stockManagement.transferContainerStock(stockTransferDTO, true);
        stockManagement.transferSkuBatchStock(stockTransferDTO, true);
    }

    private void saveTransactionRecord(StockTransferDTO stockTransferDTO) {
        ContainerStockTransactionRecord containerStockTransactionRecord = containerStockTransactionRecordTransfer
                .toDO(stockTransferDTO);
        ContainerStock containerStock = containerStockRepository.findById(stockTransferDTO.getContainerStockId());
        containerStockTransactionRecord.setSourceContainerCode(containerStock.getContainerCode());
        containerStockTransactionRecord.setSourceContainerSlotCode(containerStock.getContainerSlotCode());
        containerStockTransactionRecordRepository.save(containerStockTransactionRecord);
    }


}
