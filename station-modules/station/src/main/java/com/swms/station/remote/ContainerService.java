package com.swms.station.remote;

import com.swms.wms.api.warehouse.IContainerApi;
import com.swms.wms.api.warehouse.dto.ContainerLayoutDTO;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

@Service
public class ContainerService {

    @DubboReference
    private IContainerApi containerApi;

    public ContainerLayoutDTO queryContainer(String containerCode, String face) {
        return containerApi.queryContainerLayout(containerCode, face);
    }

    public void setContainerApi(IContainerApi iContainerApi) {
        this.containerApi = iContainerApi;
    }
}
