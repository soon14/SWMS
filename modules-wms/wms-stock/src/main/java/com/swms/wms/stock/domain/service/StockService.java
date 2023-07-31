package com.swms.wms.stock.domain.service;

import com.swms.wms.api.stock.dto.StockTransferDTO;
import com.swms.wms.stock.domain.entity.ContainerStock;

public interface StockService {

    void transferContainerStock(StockTransferDTO stockTransferDTO, ContainerStock containerStock, boolean unlock);

    void transferSkuBatchStock(StockTransferDTO stockTransferDTO, boolean unlock);

}
