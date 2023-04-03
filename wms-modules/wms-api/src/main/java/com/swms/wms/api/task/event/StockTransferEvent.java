package com.swms.wms.api.task.event;

import com.swms.wms.api.stock.dto.StockTransferDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class StockTransferEvent {
    private List<StockTransferDTO> stockTransferDTOS;
}
