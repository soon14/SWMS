package com.swms.wms.api.basic;

import com.swms.wms.api.basic.constants.WorkStationOperationTypeEnum;
import com.swms.wms.api.basic.dto.WorkStationDTO;

import java.util.List;

public interface IWorkStationApi {

    void save(WorkStationDTO workStationDTO);

    void update(WorkStationDTO workStationDTO);

    void enable(Long id);

    void disable(Long id);

    void delete(Long id);

    void online(Long id, WorkStationOperationTypeEnum operationType);

    void offline(Long id);

    void pause(Long id);

    void resume(Long id);

    WorkStationDTO queryWorkStation(Long id);

    List<WorkStationDTO> getByWarehouseCode(String warehouseCode);
}
