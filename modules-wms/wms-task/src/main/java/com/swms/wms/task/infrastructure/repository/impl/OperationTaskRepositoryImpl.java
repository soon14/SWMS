package com.swms.wms.task.infrastructure.repository.impl;

import com.swms.wms.api.task.constants.OperationTaskTypeEnum;
import com.swms.wms.task.domain.entity.OperationTask;
import com.swms.wms.task.domain.repository.OperationTaskRepository;
import com.swms.wms.task.infrastructure.persistence.mapper.OperationTaskPORepository;
import com.swms.wms.task.infrastructure.persistence.po.OperationTaskPO;
import com.swms.wms.task.infrastructure.persistence.transfer.OperationTaskPOTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperationTaskRepositoryImpl implements OperationTaskRepository {

    @Autowired
    private OperationTaskPORepository operationTaskPORepository;

    @Autowired
    private OperationTaskPOTransfer operationTaskPOTransfer;

    @Override
    public void saveAll(List<OperationTask> operationTasks) {
        operationTaskPORepository.saveAll(operationTaskPOTransfer.toOperationTaskPOs(operationTasks));
    }

    @Override
    public List<OperationTask> queryContainerTasksByTaskType(Long workStationId, List<String> containerCodes, OperationTaskTypeEnum taskType) {
        List<OperationTaskPO> operationTaskPOS = operationTaskPORepository
            .findByTaskTypeAndWorkStationIdAndSourceContainerCodeIn(taskType, workStationId, containerCodes);
        return operationTaskPOTransfer.toOperationTasks(operationTaskPOS);
    }

    @Override
    public List<OperationTask> findAllByIds(List<Long> taskIds) {
        return operationTaskPOTransfer.toOperationTasks(operationTaskPORepository.findAllById(taskIds));
    }

    @Override
    public List<OperationTask> findAllByPutWallSlotCode(String putWallSlotCode) {
        List<OperationTaskPO> operationTaskPOS = operationTaskPORepository.findAllByTargetLocationCode(putWallSlotCode);
        return operationTaskPOTransfer.toOperationTasks(operationTaskPOS);
    }
}
