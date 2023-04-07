package com.swms.wms.api.task.event;

import com.swms.utils.event.DomainEvent;
import com.swms.wms.api.stock.dto.StockTransferDTO;
import com.swms.wms.api.task.constants.OperationTaskTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockTransferEvent extends DomainEvent {
    private List<StockTransferDTO> stockTransferDTOS;

    private OperationTaskTypeEnum taskType;
}
