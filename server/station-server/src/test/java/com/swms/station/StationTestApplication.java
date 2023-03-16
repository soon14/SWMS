package com.swms.station;

import com.alibaba.cloud.commons.io.FileUtils;
import com.swms.utils.utils.JsonUtils;
import com.swms.wms.api.task.ITaskApi;
import com.swms.wms.api.task.dto.OperationTaskDTO;
import com.swms.wms.api.warehouse.IContainerApi;
import com.swms.wms.api.warehouse.IWorkStationApi;
import com.swms.wms.api.warehouse.constants.WorkStationOperationTypeEnum;
import com.swms.wms.api.warehouse.dto.ContainerLayoutDTO;
import com.swms.wms.api.warehouse.dto.WorkStationModelDTO;
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

    @Bean
    public IWorkStationApi iWorkStationApi() throws Exception {

        File file = ResourceUtils.getFile("classpath:json/WorkStationModel.json");
        String s = FileUtils.readFileToString(file, "UTF-8");
        WorkStationModelDTO workStationModelDTO = JsonUtils.string2Object(s, WorkStationModelDTO.class);

        IWorkStationApi iWorkStationApi = PowerMockito.mock(IWorkStationApi.class);
        PowerMockito.when(iWorkStationApi.queryWorkStationModel("1"))
            .thenAnswer(t -> workStationModelDTO);
        PowerMockito.doNothing().when(iWorkStationApi, "online", "1", WorkStationOperationTypeEnum.CHOICE_CONTAINER_OUTBOUND);
        PowerMockito.doNothing().when(iWorkStationApi, "offline", "1");

        return iWorkStationApi;
    }

    @Bean
    public IContainerApi iContainerApi() {

        IContainerApi iContainerApi = PowerMockito.mock(IContainerApi.class);
        PowerMockito.when(iContainerApi.queryContainerLayout("1"))
            .thenAnswer(t -> ContainerLayoutDTO.builder().containerCode("1").build());
        return iContainerApi;
    }

    @Bean
    public ITaskApi iTaskApi() throws IOException {
        File file = ResourceUtils.getFile("classpath:json/OperationTasks.json");
        String s = FileUtils.readFileToString(file, "UTF-8");
        List<OperationTaskDTO> operationTaskDTOS = JsonUtils.string2List(s, OperationTaskDTO.class);

        ITaskApi iTaskApi = PowerMockito.mock(ITaskApi.class);
        PowerMockito.when(iTaskApi.queryTaskList("1", List.of("1")))
            .thenAnswer(t -> operationTaskDTOS);
        return iTaskApi;
    }
}
