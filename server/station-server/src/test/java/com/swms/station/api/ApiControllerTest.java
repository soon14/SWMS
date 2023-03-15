package com.swms.station.api;

import com.swms.utils.utils.JsonUtils;
import com.swms.utils.utils.ObjectUtils;
import com.swms.wms.api.warehouse.constants.WorkStationOperationTypeEnum;
import com.swms.wms.api.warehouse.constants.WorkStationStatusEnum;
import com.swms.station.StationTestApplication;
import com.swms.station.remote.WorkStationService;
import com.swms.station.view.ViewHelper;
import com.swms.station.view.model.WorkStationVO;
import com.swms.station.websocket.utils.HttpContext;
import com.swms.wms.api.warehouse.IWorkStationApi;
import com.swms.wms.api.warehouse.dto.WorkStationModelDTO;
import org.assertj.core.api.Assertions;
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

    @BeforeEach
    public void initBean() {
        WorkStationService workStationService = applicationContext.getBean(WorkStationService.class);
        IWorkStationApi iWorkStationApi = applicationContext.getBean(IWorkStationApi.class);
        workStationService.setWorkStationApi(iWorkStationApi);
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

    public static void main(String[] args) {
        WorkStationModelDTO randomObject = ObjectUtils.getRandomObject(WorkStationModelDTO.class);
        System.out.println(JsonUtils.obj2StringPretty(randomObject));
    }
}
