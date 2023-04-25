package com.swms.wms.api.basic;

import com.swms.wms.api.basic.dto.ContainerSpecDTO;
import jakarta.validation.Valid;

public interface IContainerSpecApi {

    void save(@Valid ContainerSpecDTO containerSpecDTO);

    void update(@Valid ContainerSpecDTO containerSpecDTO);
}
