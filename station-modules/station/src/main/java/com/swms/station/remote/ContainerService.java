package com.swms.station.remote;

import com.swms.utils.utils.JsonUtils;
import com.swms.station.business.model.ArrivedContainer;
import com.swms.wms.api.warehouse.IContainerApi;
import com.swms.wms.api.warehouse.dto.ContainerLayoutDTO;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

@Service
public class ContainerService {

    @DubboReference
    private IContainerApi iContainerApi;

    public ContainerLayoutDTO queryContainer(String containerCode) {
        return iContainerApi.queryContainerLayout(containerCode);
    }

}
