package com.swms.wms.api.warehouse;

import com.swms.wms.api.warehouse.dto.ContainerLayoutDTO;

public interface IContainerApi {

    ContainerLayoutDTO queryContainerLayout(String containerCode, String face);
}
