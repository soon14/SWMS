package com.swms.station.api;

import com.swms.common.utils.utils.JsonUtils;
import com.swms.station.StationTestApplication;
import com.swms.station.business.handler.event.ContainerArrivedEvent;
import com.swms.station.business.handler.event.HandleTasksEvent;
import com.swms.station.business.model.ArrivedContainer;
import com.swms.station.domain.persistence.entity.WorkStation;
import com.swms.station.domain.service.WorkStationService;
import com.swms.station.remote.ContainerService;
import com.swms.station.remote.RemoteWorkStationService;
import com.swms.station.remote.TaskService;
import com.swms.station.remote.WorkStationMqConsumer;
import com.swms.station.view.ViewHelper;
import com.swms.station.view.model.WorkStationVO;
import com.swms.station.websocket.utils.HttpContext;
import com.swms.wms.api.basic.IContainerApi;
import com.swms.wms.api.basic.IWorkStationApi;
import com.swms.wms.api.basic.constants.PutWallSlotStatusEnum;
import com.swms.wms.api.basic.constants.WorkLocationTypeEnum;
import com.swms.wms.api.basic.constants.WorkStationOperationTypeEnum;
import com.swms.wms.api.basic.constants.WorkStationStatusEnum;
import com.swms.wms.api.basic.dto.PutWallDTO;
import com.swms.wms.api.task.ITaskApi;
import com.swms.wms.api.task.dto.HandleTaskDTO;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = StationTestApplication.class)
class ApiControllerTest {

    @Autowired
    private ApiController apiController;

    @Autowired
    private ViewHelper viewHelper;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private WorkStationMqConsumer workStationMqConsumer;

    @Autowired
    private WorkStationService workStationManagement;

    private static final Long WORK_STATION_ID = 1L;

    @BeforeEach
    public void initBean() {
        RemoteWorkStationService workStationService = applicationContext.getBean(RemoteWorkStationService.class);
        IWorkStationApi iWorkStationApi = applicationContext.getBean("mockIworkStationApi", IWorkStationApi.class);
        workStationService.setWorkStationApi(iWorkStationApi);

        ContainerService containerService = applicationContext.getBean(ContainerService.class);
        IContainerApi iContainerApi = applicationContext.getBean("mockIContainerApi", IContainerApi.class);
        containerService.setContainerApi(iContainerApi);

        TaskService taskService = applicationContext.getBean(TaskService.class);
        ITaskApi iTaskApi = applicationContext.getBean("mockITaskApi", ITaskApi.class);
        taskService.setTaskApi(iTaskApi);
        HttpContext.setWorkStationId(1L);
    }

    @Test
    @Order(1)
    void testOnline() {
        apiController.execute(ApiCodeEnum.ONLINE, WorkStationOperationTypeEnum.PICKING.name());

        WorkStationVO workStationVO = viewHelper.getWorkStationVO(WORK_STATION_ID);
        Assertions.assertThat(workStationVO).isNotNull();
    }


    @Test
    @Order(2)
    void testPause() {
        apiController.execute(ApiCodeEnum.PAUSE, null);
        WorkStationVO workStationVO = viewHelper.getWorkStationVO(WORK_STATION_ID);
        Assertions.assertThat(workStationVO).isNotNull();
        Assertions.assertThat(workStationVO.getWorkStationStatus()).isEqualTo(WorkStationStatusEnum.PAUSED);
    }

    @Test
    @Order(3)
    void testResume() {
        apiController.execute(ApiCodeEnum.RESUME, null);
        WorkStationVO workStationVO = viewHelper.getWorkStationVO(WORK_STATION_ID);
        Assertions.assertThat(workStationVO).isNotNull();
        Assertions.assertThat(workStationVO.getWorkStationStatus()).isEqualTo(WorkStationStatusEnum.ONLINE);
    }

    @Test
    @Order(4)
    void testOrderAssign() {
        WorkStation workStation = workStationManagement.getWorkStation(WORK_STATION_ID);
        PutWallDTO putWallDTO = workStation.getPutWalls().get(0);

        PutWallDTO.PutWallSlot putWallSlotDTO = PutWallDTO.PutWallSlot.builder()
            .putWallCode(putWallDTO.getPutWallCode())
            .putWallSlotCode(putWallDTO.getPutWallSlots().get(0).getPutWallSlotCode())
            .putWallSlotStatus(PutWallSlotStatusEnum.WAITING_BINDING)
            .workStationId(WORK_STATION_ID)
            .orderIds(Lists.newArrayList(1L)).build();

        workStationMqConsumer.listenOrderAssigned("1", Lists.newArrayList(putWallSlotDTO));

        workStation = workStationManagement.getWorkStation(WORK_STATION_ID);
        Assertions.assertThat(workStation.getPutWalls().get(0).getPutWallSlots().get(0).getOrderIds()).contains(1L);
        Assertions.assertThat(workStation.getPutWalls().get(0).getPutWallSlots().get(0).getPutWallSlotStatus())
            .isEqualTo(PutWallSlotStatusEnum.WAITING_BINDING);
    }

    @Test
    @Order(5)
    void testContainerArrive() {
        testOnline();
        ContainerArrivedEvent.ContainerDetail containerDetail = ContainerArrivedEvent.ContainerDetail.builder().containerCode("1").stationCode("1")
            .bay(1).level(1).groupCode("robot1").locationCode("1-1").workLocationType(WorkLocationTypeEnum.ROBOT)
            .workLocationCode("2").build();
        apiController.execute(ApiCodeEnum.CONTAINER_ARRIVED,
            JsonUtils.obj2String(ContainerArrivedEvent.builder().containerDetails(Lists.newArrayList(containerDetail)).build()));

        WorkStation workStation = workStationManagement.getWorkStation(WORK_STATION_ID);
        ArrivedContainer arrivedContainer = workStation.getWorkLocations().get(0).getWorkLocationSlots().get(0).getArrivedContainer();
        Assertions.assertThat(arrivedContainer).isNotNull();
        Assertions.assertThat(arrivedContainer.getContainerCode()).isEqualTo("1");
        Assertions.assertThat(workStation.getOperateTasks()).isNotEmpty();
        Assertions.assertThat(workStation.getOperateTasks().get(0).getId()).isEqualTo(1);
    }

    @Test
    @Order(6)
    void testSplitTasks() {
        testContainerArrive();

        HandleTasksEvent handleTasksEvent = HandleTasksEvent.builder().operatedQty(7)
            .handleTaskType(HandleTaskDTO.HandleTaskTypeEnum.SPLIT).taskIds(Lists.newArrayList(1L)).build();
        apiController.execute(ApiCodeEnum.HANDLE_TASKS, JsonUtils.obj2String(handleTasksEvent));

        WorkStation workStation = workStationManagement.getWorkStation(WORK_STATION_ID);
        Assertions.assertThat(workStation.getOperateTasks()).isEmpty();
    }

    @Test
    @Order(7)
    void testCompleteTasks() {

        HandleTasksEvent handleTasksEvent = HandleTasksEvent.builder().operatedQty(3)
            .handleTaskType(HandleTaskDTO.HandleTaskTypeEnum.COMPLETE).taskIds(Lists.newArrayList(1L)).build();
        apiController.execute(ApiCodeEnum.HANDLE_TASKS, JsonUtils.obj2String(handleTasksEvent));

        WorkStation workStation = workStationManagement.getWorkStation(WORK_STATION_ID);
        Assertions.assertThat(workStation.getOperateTasks()).isEmpty();
    }

    @Test
    @Order(100)
    void testOffline() {

        apiController.execute(ApiCodeEnum.OFFLINE, null);
        WorkStationVO workStationVO = viewHelper.getWorkStationVO(WORK_STATION_ID);
        Assertions.assertThat(workStationVO).isNull();
    }

    @Test
    @Order(1000)
    void testReportAbnormal() {
        testContainerArrive();

        HandleTasksEvent handleTasksEvent = HandleTasksEvent.builder().operatedQty(9)
            .handleTaskType(HandleTaskDTO.HandleTaskTypeEnum.REPORT_ABNORMAL).taskIds(Lists.newArrayList(1L)).build();
        apiController.execute(ApiCodeEnum.HANDLE_TASKS, JsonUtils.obj2String(handleTasksEvent));

        WorkStation workStation = workStationManagement.getWorkStation(WORK_STATION_ID);
        Assertions.assertThat(workStation.getOperateTasks()).isEmpty();
    }

}
