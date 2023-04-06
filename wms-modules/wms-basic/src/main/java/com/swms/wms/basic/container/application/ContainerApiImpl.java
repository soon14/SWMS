package com.swms.wms.basic.container.application;

import com.swms.wms.api.warehouse.IContainerApi;
import com.swms.wms.api.warehouse.dto.ContainerLayoutDTO;
import com.swms.wms.basic.container.domain.aggregate.ContainerAggregate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContainerApiImpl implements IContainerApi {

    @Autowired
    private ContainerAggregate containerAggregate;

    @Override
    public ContainerLayoutDTO queryContainerLayout(String containerCode, String face) {
        return containerAggregate.queryContainerLayout(containerCode, face);
    }
}
