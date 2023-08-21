package com.swms.wms.api.basic;

import com.swms.wms.api.basic.dto.ContainerSpecDTO;
import com.swms.wms.api.basic.dto.CreateContainerDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface IContainerApi {

    void createContainer(@NotNull String warehouseCode, @NotNull String containerCode, @NotNull String containerSpecCode);

    ContainerSpecDTO queryContainerLayout(@NotNull String containerCode, @NotNull String warehouseCode, String face);

    void changeContainerSpec(@NotNull String warehouseCode, @NotNull String containerCode, @NotNull String containerSpecCode);

    void createContainer(@Valid CreateContainerDTO createContainerDTO);
}
