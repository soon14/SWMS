package com.swms.wms.api.stock;

import com.swms.mdm.api.main.data.dto.SkuMainDataDTO;
import com.swms.wms.api.stock.dto.ContainerStockDTO;
import com.swms.wms.api.stock.dto.ContainerStockLockDTO;
import com.swms.wms.api.stock.dto.SkuBatchStockDTO;
import com.swms.wms.api.stock.dto.SkuBatchStockLockDTO;

import java.util.Collection;
import java.util.List;
import java.util.TreeMap;

public interface IStockApi {

    void createSkuBatchAttribute(SkuMainDataDTO skuMainDataDTO, TreeMap<String, Object> skuAttributes);

    /**
     * when receiving sku into container, create stock
     *
     * @param skuBatchStockLockDTOS
     */
    void lockSkuBatchStock(List<SkuBatchStockLockDTO> skuBatchStockLockDTOS);

    void lockContainerStock(List<ContainerStockLockDTO> containerStockLockDTOS);

    List<ContainerStockDTO> getContainerStock(String containerCode);

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
}
