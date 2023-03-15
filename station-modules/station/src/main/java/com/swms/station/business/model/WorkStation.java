package com.swms.station.business.model;

import com.swms.wms.api.warehouse.constants.ContainerLeaveTypeEnum;
import com.swms.wms.api.warehouse.constants.WorkStationOperationTypeEnum;
import com.swms.wms.api.warehouse.constants.WorkStationStatusEnum;
import com.swms.station.remote.EquipmentService;
import com.swms.station.remote.TaskService;
import com.swms.wms.api.warehouse.dto.PutWallDTO;
import com.swms.wms.api.warehouse.dto.WorkLocationDTO;
import com.swms.wms.api.warehouse.dto.WorkLocationSlotDTO;
import com.swms.wms.api.warehouse.dto.WorkStationConfigDTO;
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

    private List<WorkLocationExtend> workLocations;

    private List<PutWallDTO> putWalls;

    private List<OperateTask> operateTasks;

    private List<ArrivedContainer> arrivedContainers;

    private WorkStationConfigDTO workStationConfig;

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
                    equipmentService.containerLeave(containers.get(0), ContainerLeaveTypeEnum.LEAVE);

                    //TODO
                    // remove arrived containers
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

    /**
     * set arrived containers on the location
     *
     * @param newArrivedContainers
     */
    public void setArrivedContainersOnLocation(List<ArrivedContainer> newArrivedContainers) {
        if (CollectionUtils.isNotEmpty(workLocations)
            && StringUtils.equals(workLocations.get(0).getWorkLocationCode(), newArrivedContainers.get(0).getWorkLocationCode())) {
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
            }).collect(Collectors.toList());

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
}
