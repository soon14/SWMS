package com.swms.station.remote;

import com.swms.utils.utils.JsonUtils;
import com.swms.station.business.model.ArrivedContainer;
import com.swms.wms.api.warehouse.IContainerApi;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

@Service
public class ContainerService {

    @DubboReference
    private IContainerApi iContainerApi;

    public ArrivedContainer queryContainer(String containerCode) {
        Object container = iContainerApi.queryContainer(containerCode);
        return JsonUtils.string2Object(JsonUtils.obj2String(container), ArrivedContainer.class);
    }

}
