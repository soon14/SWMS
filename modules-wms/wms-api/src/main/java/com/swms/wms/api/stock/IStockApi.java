package com.swms.wms.api.stock;

import com.swms.mdm.api.main.data.dto.SkuMainDataDTO;
import com.swms.wms.api.stock.dto.ContainerStockDTO;
import com.swms.wms.api.stock.dto.ContainerStockLockDTO;
import com.swms.wms.api.stock.dto.SkuBatchStockDTO;
import com.swms.wms.api.stock.dto.SkuBatchStockLockDTO;
import jakarta.validation.Valid;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IStockApi {

    void createSkuBatchAttribute(SkuMainDataDTO skuMainDataDTO, Map<String, Object> skuAttributes);

    /**
     * when receiving sku into container, create stock
     *
     * @param skuBatchStockLockDTOS
     */
    void lockSkuBatchStock(@Valid List<SkuBatchStockLockDTO> skuBatchStockLockDTOS);

    void lockContainerStock(@Valid List<ContainerStockLockDTO> containerStockLockDTOS);

    void freezeContainerStock(Long id, int qty);

    void unFreezeContainerStock(Long id, int qty);


    /**
     * query sku batch stock by sku batch attribute ids
     *
     * @param skuBatchAttributeIds sku batch attribute ids
     *
     * @return
     */
    List<SkuBatchStockDTO> getBySkuBatchAttributeIds(Collection<Long> skuBatchAttributeIds);

    List<ContainerStockDTO> getContainerStock(String containerCode);

    List<ContainerStockDTO> getContainerStockBySkuBatchStockIds(List<Long> skuBatchStockIds);
}
