package com.swms.station.api;

import com.swms.common.constants.WorkStationOperationTypeEnum;
import com.swms.station.StationTestApplication;
import com.swms.station.remote.WorkStationService;
import com.swms.station.view.ViewHelper;
import com.swms.station.view.model.WorkStationVO;
import com.swms.station.websocket.utils.HttpContext;
import com.swms.wms.api.warehouse.IWorkStationApi;
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
}
