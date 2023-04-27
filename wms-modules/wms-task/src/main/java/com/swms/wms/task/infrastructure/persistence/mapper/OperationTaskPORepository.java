package com.swms.wms.task.infrastructure.persistence.mapper;

import com.swms.wms.api.task.constants.OperationTaskTypeEnum;
import com.swms.wms.task.infrastructure.persistence.po.OperationTaskPO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperationTaskPORepository extends JpaRepository<OperationTaskPO, Long> {
    List<OperationTaskPO> findByTaskTypeAndStationCodeAndSourceContainerCodeIn(OperationTaskTypeEnum taskType, String stationCode, List<String> containerCodes);

    List<OperationTaskPO> findAllByTargetLocationCode(String putWallSlotCode);
}
