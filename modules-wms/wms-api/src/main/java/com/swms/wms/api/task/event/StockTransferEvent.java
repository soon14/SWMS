package com.swms.wms.api.task.event;

import com.swms.domain.event.DomainEvent;
import com.swms.wms.api.stock.dto.StockTransferDTO;
import com.swms.wms.api.task.constants.OperationTaskTypeEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockTransferEvent extends DomainEvent {

    @NotNull
    private StockTransferDTO stockTransferDTO;

    @NotNull
    private OperationTaskTypeEnum taskType;
}
