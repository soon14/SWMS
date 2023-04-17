package com.swms.wms.task.domain.aggregate;

import com.swms.wms.api.task.dto.SealContainerDTO;
import com.swms.wms.task.domain.entity.OperationTask;
import com.swms.wms.task.domain.entity.TransferContainer;
import com.swms.wms.task.domain.repository.TransferContainerRepository;
import com.swms.wms.task.domain.service.OperationTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransferContainerAggregate {

    @Autowired
    private TransferContainerRepository transferContainerRepository;

    @Autowired
    private OperationTaskService taskService;

    @Transactional
    public void sealContainer(SealContainerDTO sealContainerDTO) {
        TransferContainer transferContainer = new TransferContainer();
        transferContainer.setTransferContainerCode(sealContainerDTO.getContainerCode());
        transferContainer.setStationCode(sealContainerDTO.getStationCode());
        transferContainer.setPutWallSlotCode(sealContainerDTO.getPutWallSlotCode());

        transferContainer.setIndex(calcIndex());
        transferContainer.setTotal(calcTotal());
        transferContainer.setDestination(calcDestination());

        List<OperationTask> operationTasks = taskService.queryContainerTasksByPutWallSlotCode(sealContainerDTO.getPutWallSlotCode());
        List<TransferContainer.TransferContainerTaskRelation> transferContainerTaskRelations = operationTasks.stream()
            .map(operationTask -> TransferContainer.TransferContainerTaskRelation.builder().transferContainerId(transferContainer.getId())
                .operationTaskId(operationTask.getId()).build()).toList();
        transferContainer.setTransferContainerTasks(transferContainerTaskRelations);

        transferContainerRepository.save(transferContainer);
    }

    private String calcDestination() {
        return "1";
    }

    private Integer calcTotal() {
        return 1;
    }

    private Integer calcIndex() {
        return 1;
    }
}
