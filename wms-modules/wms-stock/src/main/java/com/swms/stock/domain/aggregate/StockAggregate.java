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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
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

    /**
     * when stock is move from on area to another area in warehouse, then stock is transferred. e.g: picking, putAway
     * <p>attention: call this function before stock is locked </p>
     *
     * @param stockTransferDTO
     */
    @Transactional
    public void transferStock(StockTransferDTO stockTransferDTO) {

        if (transferContainerStock(stockTransferDTO)) {
            return;
        }

        transferSkuBatchStock(stockTransferDTO);
    }

    private boolean transferContainerStock(StockTransferDTO stockTransferDTO) {
        ContainerStock containerStock = containerStockRepository.findById(stockTransferDTO.getStockId());
        if (!Objects.equals(containerStock.getContainerCode(), stockTransferDTO.getTargetContainerCode())) {
            int count = containerStockLockRepository.subtractLockStock(stockTransferDTO);
            if (count < 1) {
                log.error("stock: :{} is transfer by task: {} before because of container stock lock is not exists.",
                    stockTransferDTO.getStockId(), stockTransferDTO.getTaskId());
                return true;
            }
            containerStockRepository.subtractStock(stockTransferDTO);

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
        return false;
    }

    private void transferSkuBatchStock(StockTransferDTO stockTransferDTO) {
        int count = skuBatchStockLockRepository.subtractLockStock(stockTransferDTO);
        if (count < 1) {
            log.error("stock: :{} is transfer by order detail: {} before because of sku batch stock lock is not exists.",
                stockTransferDTO.getStockId(), stockTransferDTO.getOrderDetailId());
            return;
        }
        skuBatchStockRepository.subtractStock(stockTransferDTO);

        SkuBatchStock skuBatchStock = skuBatchStockRepository.findBySkuBatchAttributeIdAndWarehouseAreaCode(
            stockTransferDTO.getSkuBatchAttributeId(), stockTransferDTO.getWarehouseAreaCode());
        if (skuBatchStock == null) {
            skuBatchStockRepository.saveAll(skuBatchStockTransfer.toSkuBatchStocks(Lists.newArrayList(stockTransferDTO)));
        } else {
            stockTransferDTO.setSkuBatchId(skuBatchStock.getId());
            skuBatchStockRepository.addStock(Lists.newArrayList(stockTransferDTO));
        }
    }

}
