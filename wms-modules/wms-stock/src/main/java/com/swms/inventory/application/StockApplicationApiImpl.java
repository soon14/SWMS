package com.swms.inventory.application;

import com.swms.inventory.domain.aggregate.StockAggregate;
import com.swms.wms.api.stock.IStockApi;
import com.swms.wms.api.stock.dto.ContainerStockLockDTO;
import com.swms.wms.api.stock.dto.SkuBatchStockLockDTO;
import com.swms.wms.api.stock.dto.StockTransferDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockApplicationApiImpl implements IStockApi {

    @Autowired
    private StockAggregate stockAggregate;

    @Override
    public void createStock(List<StockTransferDTO> stockTransferDTOS) {
        stockAggregate.createStock(stockTransferDTOS);
    }


    @Override
    public void unLockStock(List<SkuBatchStockLockDTO> skuBatchStockLockDTOS) {
    }

    @Override
    public void lockSkuBatchStock(List<SkuBatchStockLockDTO> skuBatchStockLockDTOS) {
        stockAggregate.lockSkuBatchStock(skuBatchStockLockDTOS);
    }

    @Override
    public void lockContainerStock(List<ContainerStockLockDTO> containerStockLockDTOS) {
        stockAggregate.lockContainerStock(containerStockLockDTOS);
    }

    @Override
    public void transferStock(StockTransferDTO stockDeductDTO) {
        stockAggregate.transferStock(stockDeductDTO);
    }
}
