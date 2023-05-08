package com.swms.wms.api.basic;

import com.swms.wms.api.basic.dto.ContainerSpecDTO;
import jakarta.validation.constraints.NotNull;

public interface IContainerApi {

    void createContainer(@NotNull String containerCode, @NotNull String containerSpecCode);

    ContainerSpecDTO queryContainerLayout(@NotNull String containerCode, String face);

    void changeContainerSpec(@NotNull String containerCode, @NotNull String containerSpecCode);
}
