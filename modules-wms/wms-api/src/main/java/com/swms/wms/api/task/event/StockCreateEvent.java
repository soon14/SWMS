package com.swms.wms.api.task.event;

import com.swms.domain.event.DomainEvent;
import com.swms.wms.api.stock.dto.StockCreateDTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockCreateEvent extends DomainEvent {

    @NotNull
    private StockCreateDTO stockCreateDTO;
}
