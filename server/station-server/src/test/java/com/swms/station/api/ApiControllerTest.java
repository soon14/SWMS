package com.swms.station.api;

import static org.mockito.Mockito.mock;

import com.swms.common.constants.WorkStationOperationTypeEnum;
import com.swms.common.constants.WorkStationStatusEnum;
import com.swms.common.utils.JsonUtils;
import com.swms.station.business.model.WorkStation;
import com.swms.station.view.ViewHelper;
import com.swms.station.view.model.WorkStationVO;
import com.swms.station.websocket.utils.HttpContext;
import com.swms.wms.api.warehouse.IWorkStationApi;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiControllerTest {

    @Autowired
    private ApiController apiController;

    @MockBean
    private IWorkStationApi iWorkStationApi;

    @Autowired
    private ViewHelper viewHelper;

    @BeforeEach
    public void initBean() {

        WorkStation workStation = new WorkStation();
        workStation.setStationCode("1");
        workStation.setWorkStationStatus(WorkStationStatusEnum.OFFLINE);

        Mockito.when(iWorkStationApi.queryWorkStation(Mockito.isA(String.class))).thenReturn(workStation);
        Mockito.doNothing().when(iWorkStationApi).online(Mockito.isA(String.class), Mockito.isA(WorkStationOperationTypeEnum.class));
    }

    @Test
    void testOnline() {
        HttpContext.setStationCode("1");
        apiController.execute(ApiCodeEnum.ONLINE, WorkStationOperationTypeEnum.CHOICE_CONTAINER_OUTBOUND.name());

        WorkStationVO workStationVO = viewHelper.getWorkStationVO("1");
        Assertions.assertThat(workStationVO).isNotNull();
    }

}
