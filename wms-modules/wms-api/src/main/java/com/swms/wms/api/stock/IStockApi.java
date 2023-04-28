package com.swms.wms.api.stock;

import com.swms.mdm.api.main.data.dto.SkuMainDataDTO;
import com.swms.wms.api.stock.dto.ContainerStockDTO;
import com.swms.wms.api.stock.dto.ContainerStockLockDTO;
import com.swms.wms.api.stock.dto.SkuBatchStockLockDTO;
import com.swms.wms.api.stock.dto.StockTransferDTO;

import java.util.List;
import java.util.Objects;
import java.util.TreeMap;

public interface IStockApi {

    void createSkuBatchAttribute(SkuMainDataDTO skuMainDataDTO, TreeMap<String, Object> skuAttributes);

    /**
     * when receiving sku into container, create stock
     *
     * @param stockCreateDTOS
     */
    void createStock(List<StockTransferDTO> stockCreateDTOS);

    void lockSkuBatchStock(List<SkuBatchStockLockDTO> skuBatchStockLockDTOS);

    void lockContainerStock(List<ContainerStockLockDTO> containerStockLockDTOS);

    List<ContainerStockDTO> getContainerStock(String containerCode);
}
