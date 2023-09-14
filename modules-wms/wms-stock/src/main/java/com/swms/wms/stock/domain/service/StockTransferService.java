package com.swms.wms.stock.domain.service;

import com.swms.wms.api.stock.dto.StockCreateDTO;
import com.swms.wms.api.stock.dto.StockTransferDTO;
import com.swms.wms.stock.domain.entity.ContainerStock;
import jakarta.validation.Valid;

/**
 * a combination service of container stock transfer and sku batch stock transfer
 */
public interface StockTransferService {

    /**
     * when stock is moved from outside to warehouse, then stock is created. e.g: receiving
     *
     * @param stockTransferDTO
     */
    void createStock(@Valid StockCreateDTO stockTransferDTO);

    /**
     * when stock is move from on area to another area in warehouse, then stock is transferred. e.g: picking, putAway
     * <p>attention: call this function before stock is locked </p>
     *
     * @param stockTransferDTO
     * @param containerStock
     */
    void transferStock(StockTransferDTO stockTransferDTO, ContainerStock containerStock);

    void transferAndUnlockStock(StockTransferDTO stockTransferDTO, ContainerStock containerStock);

}
