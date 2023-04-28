package com.swms.stock.application;

import com.swms.stock.domain.service.StockTransferService;
import com.swms.stock.domain.entity.ContainerStock;
import com.swms.stock.domain.entity.SkuBatchStock;
import com.swms.stock.domain.repository.ContainerStockRepository;
import com.swms.stock.domain.repository.SkuBatchStockRepository;
import com.swms.wms.api.stock.IStockApi;
import com.swms.wms.api.stock.dto.ContainerStockLockDTO;
import com.swms.wms.api.stock.dto.SkuBatchStockLockDTO;
import com.swms.wms.api.stock.dto.StockTransferDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class StockApplicationApiImpl implements IStockApi {

    @Autowired
    private StockTransferService stockTransferService;

    @Autowired
    private ContainerStockRepository containerStockRepository;

    @Autowired
    private SkuBatchStockRepository skuBatchStockRepository;

    @Override
    public void createStock(List<StockTransferDTO> stockTransferDTOS) {
        stockTransferService.createStock(stockTransferDTOS);
    }

    @Override
    public void lockSkuBatchStock(List<SkuBatchStockLockDTO> skuBatchStockLockDTOS) {
        List<Long> skuBatchStockIds = skuBatchStockLockDTOS.stream().map(SkuBatchStockLockDTO::getSkuBatchStockId).toList();
        List<SkuBatchStock> skuBatchStocks = skuBatchStockRepository.findAllByIds(skuBatchStockIds);

        skuBatchStocks.forEach(skuBatchStock -> skuBatchStockLockDTOS.forEach(skuBatchStockLockDTO -> {
            if (Objects.equals(skuBatchStock.getId(), skuBatchStockLockDTO.getSkuBatchStockId())) {
                skuBatchStock.lockQty(skuBatchStockLockDTO.getLockQty(), skuBatchStockLockDTO.getLockType());
            }
        }));
        skuBatchStockRepository.saveAll(skuBatchStocks);
    }

    @Override
    public void lockContainerStock(List<ContainerStockLockDTO> containerStockLockDTOS) {
        List<Long> containerStockIds = containerStockLockDTOS.stream().map(ContainerStockLockDTO::getContainerStockId).toList();
        List<ContainerStock> containerStocks = containerStockRepository.findAllByIds(containerStockIds);

        containerStocks.forEach(containerStock -> containerStockLockDTOS.forEach(containerStockLockDTO -> {
            if (Objects.equals(containerStock.getId(), containerStockLockDTO.getContainerStockId())) {
                containerStock.lockQty(containerStockLockDTO.getLockQty(), containerStockLockDTO.getLockType());
            }
        }));
        containerStockRepository.saveAll(containerStocks);
    }

    @Override
    public void transferStock(StockTransferDTO stockDeductDTO) {
        stockTransferService.transferStock(stockDeductDTO);
    }
}
