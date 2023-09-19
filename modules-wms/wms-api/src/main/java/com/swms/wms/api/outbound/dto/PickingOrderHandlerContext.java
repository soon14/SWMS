package com.swms.wms.api.outbound.dto;

import com.swms.wms.api.basic.dto.WorkStationDTO;
import com.swms.wms.api.stock.dto.ContainerStockDTO;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class PickingOrderHandlerContext {

    @NotEmpty
    private List<PickingOrderDTO> pickingOrders;

    @NotEmpty
    private List<WorkStationDTO> workStations;

    private List<ContainerStockDTO> containerStocks;
}
