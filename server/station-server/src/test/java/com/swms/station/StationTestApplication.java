package com.swms.station;

import com.swms.common.constants.WorkStationOperationTypeEnum;
import com.swms.common.constants.WorkStationStatusEnum;
import com.swms.station.business.model.WorkStation;
import com.swms.wms.api.warehouse.IWorkStationApi;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = {"com.swms"})
public class StationTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(StationTestApplication.class, args);
    }

    @Bean
    public IWorkStationApi iWorkStationApi() throws Exception {

        WorkStation workStation = new WorkStation();
        workStation.setStationCode("1");
        workStation.setWorkStationStatus(WorkStationStatusEnum.OFFLINE);

        IWorkStationApi iWorkStationApi = PowerMockito.mock(IWorkStationApi.class);

        PowerMockito.when(iWorkStationApi.queryWorkStation("1"))
            .thenAnswer(t -> workStation);
        PowerMockito.doNothing().when(iWorkStationApi, "online", "1", WorkStationOperationTypeEnum.CHOICE_CONTAINER_OUTBOUND);
        PowerMockito.doNothing().when(iWorkStationApi, "offline", "1");

        return iWorkStationApi;
    }
}
