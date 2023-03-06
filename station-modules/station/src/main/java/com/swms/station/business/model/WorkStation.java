package com.swms.station.business.model;

import com.google.common.collect.Lists;
import com.swms.common.constants.ContainerLeaveType;
import com.swms.common.constants.WorkStationOperationTypeEnum;
import com.swms.common.constants.WorkStationStatusEnum;
import com.swms.station.remote.EquipmentService;
import com.swms.station.remote.TaskService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * definition：a place that operators working, only support one station one Operation Type at a time.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class WorkStation {

    private String stationCode;

    private WorkStationStatusEnum workStationStatus;

    private String warehouseCode;
    private String warehouseAreaCode;
    private String warehouseLogicCode;

    private WorkStationOperationTypeEnum operationType;

    private List<WorkLocation> workLocations;

    private List<PutWall> putWalls;

    private List<OperateTask> operateTasks;

    private List<ArrivedContainer> arrivedContainers;

    private WorkStationConfig workStationConfig;

    @Autowired
    private TaskService taskService;

    @Autowired
    private EquipmentService equipmentService;

    public void handleUndoContainers() {

        synchronized (this) {
            if (CollectionUtils.isEmpty(arrivedContainers)) {
                return;
            }

            while (true) {
                List<ArrivedContainer> undoContainers = getUndoContainers(arrivedContainers);
                if (CollectionUtils.isEmpty(undoContainers)) {
                    break;
                }
                // query tasks by container code
                List<OperateTask> containerOperateTasks = taskService.queryTasks(stationCode, undoContainers.stream().map(ArrivedContainer::getContainerCode).toList());

                if (containerOperateTasks != null) {
                    if (CollectionUtils.isEmpty(this.getOperateTasks())) {
                        this.setOperateTasks(containerOperateTasks);
                    } else {
                        this.getOperateTasks().addAll(containerOperateTasks);
                    }
                    arrivedContainers.forEach(arrivedContainer -> {
                        if (undoContainers.stream().anyMatch(undoContainer -> undoContainer.getContainerCode().equals(arrivedContainer.getContainerCode()))) {
                            arrivedContainer.setProcessStatus(1);
                        }
                    });

                    break;
                } else {
                    arrivedContainers.forEach(arrivedContainer -> {
                        if (undoContainers.stream().anyMatch(undoContainer -> undoContainer.getContainerCode().equals(arrivedContainer.getContainerCode()))) {
                            arrivedContainer.setProcessStatus(2);
                        }
                    });
                }
            }

            // group arrived containers by group code
            arrivedContainers.stream().collect(Collectors.groupingBy(ArrivedContainer::getGroupCode)).forEach((groupCode, containers) -> {
                if (containers.stream().allMatch(v -> v.getProcessStatus() == 2)) {
                    // all containers are done, let them leave
                    equipmentService.containerLeave(containers.get(0), ContainerLeaveType.LEAVE);
                }
            });
        }
    }

    private List<ArrivedContainer> getUndoContainers(List<ArrivedContainer> arrivedContainers) {
        List<ArrivedContainer> undoContainers = arrivedContainers.stream().filter(v -> v.getProcessStatus() == 0).toList();

        if (operationType == WorkStationOperationTypeEnum.ONE_STEP_INVENTORY_RELOCATION) {
            undoContainers = undoContainers.subList(0, 2);
        } else {
            undoContainers = undoContainers.subList(0, 1);
        }
        return undoContainers;
    }
}
