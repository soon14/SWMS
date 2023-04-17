package com.swms.wms.task.infrastructure.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.swms.wms.api.task.constants.OperationTaskTypeEnum;
import com.swms.wms.task.domain.entity.OperationTask;
import com.swms.wms.task.domain.repository.OperationTaskRepository;
import com.swms.wms.task.infrastructure.persistence.mapper.OperationTaskMapper;
import com.swms.wms.task.infrastructure.persistence.po.OperationTaskPO;
import com.swms.wms.task.infrastructure.persistence.transfer.OperationTaskPOTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperationTaskRepositoryImpl implements OperationTaskRepository {

    @Autowired
    private OperationTaskMapper operationTaskMapper;

    @Autowired
    private OperationTaskPOTransfer operationTaskPOTransfer;

    @Override
    public void saveAll(List<OperationTask> operationTasks) {

    }

    @Override
    public List<OperationTask> queryContainerTasksByTaskType(String stationCode, List<String> containerCodes, OperationTaskTypeEnum taskType) {
        LambdaQueryWrapper<OperationTaskPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OperationTaskPO::getStationCode, stationCode);
        wrapper.eq(OperationTaskPO::getTaskType, taskType);
        wrapper.in(OperationTaskPO::getSourceContainerCode, containerCodes);
        List<OperationTaskPO> operationTaskPOS = operationTaskMapper.selectList(wrapper);
        return operationTaskPOTransfer.toOperationTasks(operationTaskPOS);
    }

    @Override
    public List<OperationTask> findAllByIds(List<Long> taskIds) {
        return operationTaskPOTransfer.toOperationTasks(operationTaskMapper.selectBatchIds(taskIds));
    }

    @Override
    public List<OperationTask> findAllByPutWallSlotCode(String putWallSlotCode) {
        LambdaQueryWrapper<OperationTaskPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OperationTaskPO::getTargetLocationCode, putWallSlotCode);
        List<OperationTaskPO> operationTaskPOS = operationTaskMapper.selectList(wrapper);
        return operationTaskPOTransfer.toOperationTasks(operationTaskPOS);
    }
}
