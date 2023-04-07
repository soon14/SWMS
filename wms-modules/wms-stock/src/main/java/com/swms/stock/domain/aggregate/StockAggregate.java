package com.swms.stock.domain.aggregate;

import com.swms.stock.domain.repository.ContainerStockRepository;
import com.swms.stock.domain.repository.SkuBatchStockRepository;
import com.swms.stock.domain.service.StockManagement;
import com.swms.stock.domain.transfer.ContainerStockTransfer;
import com.swms.wms.api.stock.dto.ContainerStockLockDTO;
import com.swms.wms.api.stock.dto.SkuBatchStockLockDTO;
import com.swms.wms.api.stock.dto.StockTransferDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class StockAggregate {

    @Autowired
    private SkuBatchStockRepository skuBatchStockRepository;

    @Autowired
    private ContainerStockRepository containerStockRepository;

    @Autowired
    private StockManagement stockManagement;

    @Autowired
    private ContainerStockTransfer containerStockTransfer;

    /**
     * when stock is moved from outside to warehouse, then stock is created. e.g: receiving
     *
     * @param stockTransferDTOS
     */
    @Transactional
    public void createStock(List<StockTransferDTO> stockTransferDTOS) {
        containerStockRepository.saveAll(containerStockTransfer.toContainerStocks(stockTransferDTOS));
        skuBatchStockRepository.addStock(stockTransferDTOS);
    }

    @Transactional
    public void lockSkuBatchStock(List<SkuBatchStockLockDTO> skuBatchStockLockDTOS) {
        skuBatchStockRepository.lockStock(skuBatchStockLockDTOS);
    }

    @Transactional
    public void lockContainerStock(List<ContainerStockLockDTO> containerStockLockDTOS) {
        containerStockRepository.lockStock(containerStockLockDTOS);
    }

    /**
     * when stock is move from on area to another area in warehouse, then stock is transferred. e.g: picking, putAway
     * <p>attention: call this function before stock is locked </p>
     *
     * @param stockTransferDTO
     */
    @Transactional
    public void transferStock(StockTransferDTO stockTransferDTO) {
        stockManagement.transferContainerStock(stockTransferDTO, false);
        stockManagement.transferSkuBatchStock(stockTransferDTO, false);
    }

    @Transactional
    public void transferAndUnlockStock(StockTransferDTO stockTransferDTO) {
        stockManagement.transferContainerStock(stockTransferDTO, true);
        stockManagement.transferSkuBatchStock(stockTransferDTO, true);
    }


}
