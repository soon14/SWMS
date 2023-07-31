package com.swms.wms.api.task.event;

import com.swms.wms.api.stock.dto.StockCreateDTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockCreateEvent {

    @NotNull
    private List<StockCreateDTO> stockCreateDTOS;
}
