package com.swms.stock.domain.aggregate;

import com.google.common.collect.Lists;
import com.swms.stock.domain.entity.ContainerStock;
import com.swms.stock.domain.entity.SkuBatchStock;
import com.swms.stock.domain.repository.ContainerStockLockRepository;
import com.swms.stock.domain.repository.ContainerStockRepository;
import com.swms.stock.domain.repository.SkuBatchStockLockRepository;
import com.swms.stock.domain.repository.SkuBatchStockRepository;
import com.swms.stock.domain.transfer.ContainerStockLockTransfer;
import com.swms.stock.domain.transfer.ContainerStockTransfer;
import com.swms.stock.domain.transfer.SkuBatchStockLockTransfer;
import com.swms.stock.domain.transfer.SkuBatchStockTransfer;
import com.swms.wms.api.stock.dto.ContainerStockLockDTO;
import com.swms.wms.api.stock.dto.SkuBatchStockLockDTO;
import com.swms.wms.api.stock.dto.StockTransferDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class StockAggregate {

    @Autowired
    private SkuBatchStockRepository skuBatchStockRepository;

    @Autowired
    private SkuBatchStockLockRepository skuBatchStockLockRepository;

    @Autowired
    private ContainerStockRepository containerStockRepository;

    @Autowired
    private ContainerStockLockRepository containerStockLockRepository;

    @Autowired
    private ContainerStockLockTransfer containerStockLockTransfer;

    @Autowired
    private ContainerStockTransfer containerStockTransfer;

    @Autowired
    private SkuBatchStockLockTransfer skuBatchStockLockTransfer;

    @Autowired
    private SkuBatchStockTransfer skuBatchStockTransfer;

    @Transactional
    public void createStock(List<StockTransferDTO> stockTransferDTOS) {
        containerStockRepository.saveAll(containerStockTransfer.toContainerStocks(stockTransferDTOS));
        skuBatchStockRepository.addStock(stockTransferDTOS);
    }

    @Transactional
    public void lockSkuBatchStock(List<SkuBatchStockLockDTO> skuBatchStockLockDTOS) {
        skuBatchStockRepository.lockStock(skuBatchStockLockDTOS);
        skuBatchStockLockRepository.saveAll(skuBatchStockLockTransfer.toSkuBatchStockLocks(skuBatchStockLockDTOS));
    }

    @Transactional
    public void lockContainerStock(List<ContainerStockLockDTO> containerStockLockDTOS) {
        containerStockRepository.lockStock(containerStockLockDTOS);
        containerStockLockRepository.saveAll(containerStockLockTransfer.toContainerStockLocks(containerStockLockDTOS));
    }

    @Transactional
    public void transferStock(List<StockTransferDTO> stockTransferDTOS) {
    }

    @Transactional
    public void transferStock(StockTransferDTO stockTransferDTO) {
        skuBatchStockRepository.subtractStock(stockTransferDTO);
        skuBatchStockLockRepository.subtractLockStock(stockTransferDTO);

        SkuBatchStock skuBatchStock = skuBatchStockRepository.findBySkuBatchAttributeIdAndWarehouseAreaCode(
            stockTransferDTO.getSkuBatchAttributeId(), stockTransferDTO.getWarehouseAreaCode());
        // warehouse area is existing
        if (skuBatchStock == null) {
            skuBatchStockRepository.saveAll(skuBatchStockTransfer.toSkuBatchStocks(Lists.newArrayList(stockTransferDTO)));
        } else {
            stockTransferDTO.setSkuBatchId(skuBatchStock.getId());
            skuBatchStockRepository.addStock(Lists.newArrayList(stockTransferDTO));
        }

        ContainerStock containerStock = containerStockRepository.findById(stockTransferDTO.getStockId());
        if (!Objects.equals(containerStock.getContainerId(), stockTransferDTO.getTargetContainerId())) {
            containerStockRepository.subtractStock(stockTransferDTO);
            containerStockLockRepository.subtractLockStock(stockTransferDTO);

            containerStockRepository.saveAll(containerStockTransfer.toContainerStocks(Lists.newArrayList(stockTransferDTO)));
        }
    }

}
