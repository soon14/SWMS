package com.swms.station.api;

import com.swms.station.business.handler.event.ContainerArrivedEvent;
import com.swms.station.business.model.ArrivedContainer;
import com.swms.station.business.model.WorkStation;
import com.swms.station.business.model.WorkStationManagement;
import com.swms.station.remote.ContainerService;
import com.swms.station.remote.TaskService;
import com.swms.station.remote.WorkStationMqConsumer;
import com.swms.utils.utils.JsonUtils;
import com.swms.utils.utils.ObjectUtils;
import com.swms.wms.api.task.ITaskApi;
import com.swms.wms.api.task.dto.OperationTaskDTO;
import com.swms.wms.api.warehouse.IContainerApi;
import com.swms.wms.api.warehouse.constants.PutWallSlotStatusEnum;
import com.swms.wms.api.warehouse.constants.WorkLocationTypeEnum;
import com.swms.wms.api.warehouse.constants.WorkStationOperationTypeEnum;
import com.swms.wms.api.warehouse.constants.WorkStationStatusEnum;
import com.swms.station.StationTestApplication;
import com.swms.station.remote.WorkStationService;
import com.swms.station.view.ViewHelper;
import com.swms.station.view.model.WorkStationVO;
import com.swms.station.websocket.utils.HttpContext;
import com.swms.wms.api.warehouse.IWorkStationApi;
import com.swms.wms.api.warehouse.dto.PutWallDTO;
import com.swms.wms.api.warehouse.dto.PutWallSlotDTO;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
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
    private WorkStationManagement workStationManagement;

    @BeforeEach
    public void initBean() {
        WorkStationService workStationService = applicationContext.getBean(WorkStationService.class);
        IWorkStationApi iWorkStationApi = applicationContext.getBean(IWorkStationApi.class);
        workStationService.setWorkStationApi(iWorkStationApi);

        ContainerService containerService = applicationContext.getBean(ContainerService.class);
        IContainerApi iContainerApi = applicationContext.getBean(IContainerApi.class);
        containerService.setContainerApi(iContainerApi);

        TaskService taskService = applicationContext.getBean(TaskService.class);
        ITaskApi iTaskApi = applicationContext.getBean(ITaskApi.class);
        taskService.setTaskApi(iTaskApi);
    }

    @Test
    void testOnline() {
        HttpContext.setStationCode("1");
        apiController.execute(ApiCodeEnum.ONLINE, WorkStationOperationTypeEnum.CHOICE_CONTAINER_OUTBOUND.name());

        WorkStationVO workStationVO = viewHelper.getWorkStationVO("1");
        Assertions.assertThat(workStationVO).isNotNull();
    }

    @Test
    void testOffline() {
        testOnline();

        apiController.execute(ApiCodeEnum.OFFLINE, null);
        WorkStationVO workStationVO = viewHelper.getWorkStationVO("1");
        Assertions.assertThat(workStationVO).isNull();
    }

    @Test
    void testPause() {
        testOnline();

        apiController.execute(ApiCodeEnum.PAUSE, null);
        WorkStationVO workStationVO = viewHelper.getWorkStationVO("1");
        Assertions.assertThat(workStationVO).isNotNull();
        Assertions.assertThat(workStationVO.getWorkStationStatus()).isEqualTo(WorkStationStatusEnum.PAUSED);
    }

    @Test
    void testResume() {
        testPause();

        apiController.execute(ApiCodeEnum.RESUME, null);
        WorkStationVO workStationVO = viewHelper.getWorkStationVO("1");
        Assertions.assertThat(workStationVO).isNotNull();
        Assertions.assertThat(workStationVO.getWorkStationStatus()).isEqualTo(WorkStationStatusEnum.ONLINE);

    }

    @Test
    void testOrderAssign() {
        testOnline();

        WorkStation workStation = workStationManagement.getWorkStation("1");
        PutWallDTO putWallDTO = workStation.getPutWalls().get(0);

        PutWallSlotDTO putWallSlotDTO = PutWallSlotDTO.builder()
            .putWallCode(putWallDTO.getPutWallCode())
            .slotCode(putWallDTO.getPutWallSlots().get(0).getSlotCode())
            .putWallSlotStatus(PutWallSlotStatusEnum.WAITING_BINDING)
            .stationCode("1")
            .orderIds(Lists.newArrayList(1L)).build();

        workStationMqConsumer.listenOrderAssigned("1", Lists.newArrayList(putWallSlotDTO));

        workStation = workStationManagement.getWorkStation("1");
        Assertions.assertThat(workStation.getPutWalls().get(0).getPutWallSlots().get(0).getOrderIds()).contains(1L);
        Assertions.assertThat(workStation.getPutWalls().get(0).getPutWallSlots().get(0).getPutWallSlotStatus())
            .isEqualTo(PutWallSlotStatusEnum.WAITING_BINDING);
    }

    @Test
    void testContainerArrive() {
        testOnline();
        ContainerArrivedEvent containerArrivedEvent = ContainerArrivedEvent.builder().containerCode("1").stationCode("1")
            .bay(1).level(1).groupCode("robot1").locationCode("1-1").workLocationType(WorkLocationTypeEnum.ROBOT)
            .workLocationCode("2").build();
        apiController.execute(ApiCodeEnum.CONTAINER_ARRIVED, JsonUtils.obj2String(Lists.newArrayList(containerArrivedEvent)));

        WorkStation workStation = workStationManagement.getWorkStation("1");
        ArrivedContainer arrivedContainer = workStation.getWorkLocations().get(0).getWorkLocationSlots().get(0).getArrivedContainer();
        Assertions.assertThat(arrivedContainer).isNotNull();
        Assertions.assertThat(arrivedContainer.getContainerCode()).isEqualTo("1");
        Assertions.assertThat(workStation.getOperateTasks()).isNotEmpty();
        Assertions.assertThat(workStation.getOperateTasks().get(0).getTaskId()).isEqualTo(1);
    }

    @Test
    void testCompleteTasks() {
        testContainerArrive();
        apiController.execute(ApiCodeEnum.COMPLETE_TASKS, JsonUtils.obj2String(Lists.newArrayList(1L)));

        WorkStation workStation = workStationManagement.getWorkStation("1");
        Assertions.assertThat(workStation.getOperateTasks()).isEmpty();
    }

    @Test
    void fullFlowTest() {
        testOnline();
        testOrderAssign();
    }

    public static void main(String[] args) {
        System.out.println(JsonUtils.obj2StringPretty(ObjectUtils.getRandomObject(OperationTaskDTO.class)));
    }

}
