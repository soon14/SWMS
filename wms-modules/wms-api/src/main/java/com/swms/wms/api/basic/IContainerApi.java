package com.swms.wms.api.basic;

import com.swms.wms.api.basic.dto.ContainerLayoutDTO;

public interface IContainerApi {

    ContainerLayoutDTO queryContainerLayout(String containerCode, String face);
}
