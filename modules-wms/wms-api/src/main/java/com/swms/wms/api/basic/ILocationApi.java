package com.swms.wms.api.basic;

import com.swms.wms.api.basic.dto.AisleDTO;
import com.swms.wms.api.basic.dto.LocationDTO;
import com.swms.wms.api.basic.dto.LocationUpdateDTO;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public interface ILocationApi {

    void createLocations(@NotEmpty List<AisleDTO> aisleDTOS, @NotEmpty List<LocationDTO> locationDTOS);

    void update(LocationUpdateDTO locationUpdateDTO);

    void delete(Long id);

    List<LocationDTO> getByAisle(String aisleCode, Long warehouseAreaId);

    void deleteByAisle(String aisleCode, Long warehouseAreaId);

}
