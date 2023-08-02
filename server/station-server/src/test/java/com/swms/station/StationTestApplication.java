package com.swms.station;

import com.alibaba.cloud.commons.io.FileUtils;
import com.swms.common.utils.utils.JsonUtils;
import com.swms.wms.api.basic.IContainerApi;
import com.swms.wms.api.basic.IWorkStationApi;
import com.swms.wms.api.basic.constants.WorkStationOperationTypeEnum;
import com.swms.wms.api.basic.dto.ContainerDTO;
import com.swms.wms.api.basic.dto.WorkStationDTO;
import com.swms.wms.api.task.ITaskApi;
import com.swms.wms.api.task.constants.OperationTaskTypeEnum;
import com.swms.wms.api.task.dto.OperationTaskDTO;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

@SpringBootApplication(scanBasePackages = {"com.swms"})
public class StationTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(StationTestApplication.class, args);
    }

    public final static String WAREHOUSE_CODE = "123";

    private final Long workStationId = 1L;

    @Bean("mockIworkStationApi")
    public IWorkStationApi iWorkStationApi() throws Exception {

        File file = ResourceUtils.getFile("classpath:json/WorkStationModel.json");
        String s = FileUtils.readFileToString(file, "UTF-8");
        WorkStationDTO workStationModelDTO = JsonUtils.string2Object(s, WorkStationDTO.class);

        IWorkStationApi iWorkStationApi = PowerMockito.mock(IWorkStationApi.class);
        PowerMockito.when(iWorkStationApi.queryWorkStation(workStationId))
            .thenAnswer(t -> workStationModelDTO);
        PowerMockito.doNothing().when(iWorkStationApi, "online", "1", WorkStationOperationTypeEnum.PICKING);
        PowerMockito.doNothing().when(iWorkStationApi, "offline", "1");

        return iWorkStationApi;
    }

    @Bean("mockIContainerApi")
    public IContainerApi iContainerApi() {

        IContainerApi iContainerApi = PowerMockito.mock(IContainerApi.class);
        PowerMockito.when(iContainerApi.queryContainerLayout("1", WAREHOUSE_CODE, "A"))
            .thenAnswer(t -> ContainerDTO.builder().containerCode("1").build());
        return iContainerApi;
    }

    @Bean("mockITaskApi")
    public ITaskApi iTaskApi() throws IOException {
        File file = ResourceUtils.getFile("classpath:json/OperationTasks.json");
        String s = FileUtils.readFileToString(file, "UTF-8");
        List<OperationTaskDTO> operationTaskDTOS = JsonUtils.string2List(s, OperationTaskDTO.class);

        ITaskApi iTaskApi = PowerMockito.mock(ITaskApi.class);
        PowerMockito.when(iTaskApi.queryTasks("1", List.of("1"), OperationTaskTypeEnum.PICKING))
            .thenAnswer(t -> operationTaskDTOS);
        return iTaskApi;
    }
}
