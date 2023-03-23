package com.swms.wms.task.domain.aggregate;

import com.swms.wms.api.task.dto.SealContainerDTO;
import com.swms.wms.task.domain.entity.OperationTask;
import com.swms.wms.task.domain.entity.TransferContainer;
import com.swms.wms.task.domain.entity.TransferContainerTaskRelation;
import com.swms.wms.task.domain.repository.TransferContainerRepository;
import com.swms.wms.task.domain.repository.TransferContainerTaskRelationRepository;
import com.swms.wms.task.domain.service.OperationTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransferContainerAggregate {

//    private Long id;
//    private String transferContainerCode;
//    private String stationCode;
//    private String putWallSlot;
//
//    private Integer index;
//    private Integer total;
//
//    private String destination;
//
//    private List<OperationTask> operationTasks;

//    @Autowired
//    private List<TransferContainerTaskRelation> transferContainerTaskRelations;

    @Autowired
    private TransferContainerRepository transferContainerRepository;

    @Autowired
    private TransferContainerTaskRelationRepository transferContainerTaskRelationRepository;

    @Autowired
    private OperationTaskService taskService;

    public void sealContainer(SealContainerDTO sealContainerDTO) {
        //1. create transfer container
        TransferContainer transferContainer = new TransferContainer();
        transferContainer.setTransferContainerCode(sealContainerDTO.getContainerCode());
        transferContainer.setStationCode(sealContainerDTO.getStationCode());
        transferContainer.setPutWallSlotCode(sealContainerDTO.getPutWallSlotCode());

        transferContainer.setIndex(calcIndex());
        transferContainer.setTotal(calcTotal());
        transferContainer.setDestination(calcDestination());

        transferContainerRepository.save(transferContainer);

        //2. create transfer container and operation task relationship
        List<OperationTask> operationTasks = taskService.queryContainerTasksByPutWallSlotCode(sealContainerDTO.getPutWallSlotCode());
        List<TransferContainerTaskRelation> transferContainerTaskRelations = operationTasks.stream().map(operationTask -> {
            return TransferContainerTaskRelation.builder().transferContainerId(transferContainer.getId())
                .operationTaskId(operationTask.getId()).build();
        }).toList();
        transferContainerTaskRelationRepository.saveAll(transferContainerTaskRelations);

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
