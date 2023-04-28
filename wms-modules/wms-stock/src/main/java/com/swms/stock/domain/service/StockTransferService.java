package com.swms.stock.domain.service;

import com.swms.wms.api.stock.dto.StockTransferDTO;

import java.util.List;

/**
 * a combination service of container stock transfer and sku batch stock transfer
 */
public interface StockTransferService {

    /**
     * when stock is moved from outside to warehouse, then stock is created. e.g: receiving
     *
     * @param stockTransferDTOS
     */
    void createStock(List<StockTransferDTO> stockTransferDTOS);

    /**
     * when stock is move from on area to another area in warehouse, then stock is transferred. e.g: picking, putAway
     * <p>attention: call this function before stock is locked </p>
     *
     * @param stockTransferDTO
     */
    void transferStock(StockTransferDTO stockTransferDTO);

    void transferAndUnlockStock(StockTransferDTO stockTransferDTO);

}
