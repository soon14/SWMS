package com.swms.station.business.model;

import com.swms.station.remote.EquipmentService;
import com.swms.station.remote.TaskService;
import com.swms.wms.api.task.constants.OperationTaskTypeEnum;
import com.swms.wms.api.task.dto.OperationTaskDTO;
import com.swms.wms.api.warehouse.constants.ContainerLeaveTypeEnum;
import com.swms.wms.api.warehouse.constants.WorkStationOperationTypeEnum;
import com.swms.wms.api.warehouse.constants.WorkStationStatusEnum;
import com.swms.wms.api.warehouse.dto.PutWallDTO;
import com.swms.wms.api.warehouse.dto.WorkStationConfigDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * definition：a place that operators working, only support one station one Operation Type at a time.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkStation {

    private String stationCode;

    private WorkStationStatusEnum workStationStatus;

    private String warehouseCode;
    private String warehouseAreaCode;
    private String warehouseLogicCode;

    private WorkStationOperationTypeEnum operationType;

    private List<WorkLocationExtend> workLocations;

    private List<PutWallDTO> putWalls;

    private List<OperationTaskDTO> operateTasks;

    private List<ArrivedContainer> arrivedContainers;

    private WorkStationConfigDTO workStationConfig;

    public void handleUndoContainers(TaskService taskService, EquipmentService equipmentService) {

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
                List<OperationTaskDTO> containerOperateTasks = taskService.queryTasks(stationCode, undoContainers.stream().map(ArrivedContainer::getContainerCode).toList(), getOperationTaskType());

                if (containerOperateTasks != null) {
                    if (CollectionUtils.isEmpty(this.getOperateTasks())) {
                        this.setOperateTasks(containerOperateTasks);
                    } else {
                        this.getOperateTasks().addAll(containerOperateTasks);
                    }
                    arrivedContainers.forEach(arrivedContainer -> {
                        if (undoContainers.stream().anyMatch(undoContainer -> StringUtils.equals(undoContainer.getContainerCode(), arrivedContainer.getContainerCode()))) {
                            arrivedContainer.setProcessStatus(1);
                        }
                    });

                    break;
                } else {
                    arrivedContainers.forEach(arrivedContainer -> {
                        if (undoContainers.stream().anyMatch(undoContainer -> StringUtils.equals(undoContainer.getContainerCode(), arrivedContainer.getContainerCode()))) {
                            arrivedContainer.setProcessStatus(2);
                        }
                    });
                }
            }

            // group arrived containers by group code
            arrivedContainers.stream().collect(Collectors.groupingBy(ArrivedContainer::getGroupCode)).forEach((groupCode, containers) -> {
                if (containers.stream().allMatch(v -> v.getProcessStatus() == 2)) {
                    // all containers are done, let them leave
                    equipmentService.containerLeave(containers.get(0), ContainerLeaveTypeEnum.LEAVE);

                    //TODO
                    // remove arrived containers
                }
            });
        }
    }

    public OperationTaskTypeEnum getOperationTaskType() {
        if (operationType == WorkStationOperationTypeEnum.ONE_STEP_INVENTORY_RELOCATION) {
            return OperationTaskTypeEnum.ONE_STEP_RELOCATION;
        } else if (operationType == WorkStationOperationTypeEnum.TWO_STEP_INVENTORY_RELOCATION) {
            return OperationTaskTypeEnum.TWO_STEP_RELOCATION;
        } else if (operationType == WorkStationOperationTypeEnum.OUTBOUND) {
            return OperationTaskTypeEnum.PICKING;
        } else if (operationType == WorkStationOperationTypeEnum.STOCK_TAKE) {
            return OperationTaskTypeEnum.COUNTING;
        }
        return null;
    }

    private List<ArrivedContainer> getUndoContainers(List<ArrivedContainer> arrivedContainers) {
        List<ArrivedContainer> undoContainers = arrivedContainers.stream().filter(v -> v.getProcessStatus() == 0).toList();

        if (CollectionUtils.isEmpty(undoContainers)) {
            return Collections.emptyList();
        }

        if (operationType == WorkStationOperationTypeEnum.ONE_STEP_INVENTORY_RELOCATION) {
            undoContainers = undoContainers.subList(0, 2);
        } else {
            undoContainers = undoContainers.subList(0, 1);
        }
        return undoContainers;
    }

    /**
     * set arrived containers on the location
     *
     * @param newArrivedContainers
     */
    public void setArrivedContainersOnLocation(List<ArrivedContainer> newArrivedContainers) {
        if (CollectionUtils.isNotEmpty(workLocations)
            && workLocations.stream().anyMatch(workLocation -> StringUtils.equals(workLocation.getWorkLocationCode(), newArrivedContainers.get(0).getWorkLocationCode()))) {
            newArrivedContainers.forEach(arrivedContainer -> {
                workLocations.stream().flatMap(workLocation -> workLocation.getWorkLocationSlots()
                    .stream()).forEach(workLocationSlotExtend -> {
                    if (workLocationSlotExtend.getSlotCode().equals(arrivedContainer.getLocationCode())) {
                        workLocationSlotExtend.setArrivedContainer(arrivedContainer);
                    }
                });
            });
        } else {
            WorkLocationExtend workLocationExtend = new WorkLocationExtend();
            List<WorkLocationExtend.WorkLocationSlotExtend> workLocationSlotExtends = newArrivedContainers.stream().map(arrivedContainer -> {
                WorkLocationExtend.WorkLocationSlotExtend workLocationSlotExtend = new WorkLocationExtend.WorkLocationSlotExtend();
                workLocationSlotExtend.setSlotCode(arrivedContainer.getLocationCode());
                workLocationSlotExtend.setArrivedContainer(arrivedContainer);
                workLocationSlotExtend.setLevel(arrivedContainer.getLevel());
                workLocationSlotExtend.setBay(arrivedContainer.getBay());
                workLocationSlotExtend.setGroupCode(arrivedContainer.getGroupCode());
                workLocationSlotExtend.setWorkLocationCode(arrivedContainer.getWorkLocationCode());
                return workLocationSlotExtend;
            }).toList();

            workLocationExtend.setWorkLocationSlots(workLocationSlotExtends);
            workLocationExtend.setWorkLocationType(newArrivedContainers.get(0).getWorkLocationType());
            workLocationExtend.setWorkLocationCode(newArrivedContainers.get(0).getWorkLocationCode());
            workLocationExtend.setEnable(true);
            workLocationExtend.setStationCode(stationCode);

            if (CollectionUtils.isNotEmpty(workLocations)) {
                workLocations.add(workLocationExtend);
            } else {
                this.setWorkLocations(List.of(workLocationExtend));
            }
        }
    }

    public void removeOperateTasks(List<Long> taskIds) {
        this.operateTasks.removeIf(operationTaskDTO -> taskIds.contains(operationTaskDTO.getTaskId()));
    }

    public void addArrivedContainers(List<ArrivedContainer> newArrivedContainers) {
        if (CollectionUtils.isEmpty(this.arrivedContainers)) {
            this.setArrivedContainers(newArrivedContainers);
        } else {
            this.arrivedContainers.addAll(newArrivedContainers);
        }
    }
}
