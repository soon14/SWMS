package com.swms.wms.basic.container.infrastructure.repository.impl;

import com.swms.wms.basic.container.domain.entity.Container;
import com.swms.wms.basic.container.domain.repository.ContainerRepository;
import com.swms.wms.basic.container.infrastructure.persistence.mapper.ContainerMapper;
import com.swms.wms.basic.container.infrastructure.persistence.transfer.ContainerPOTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContainerRepositoryImpl implements ContainerRepository {

    @Autowired
    private ContainerMapper containerMapper;

    @Autowired
    private ContainerPOTransfer containerPOTransfer;

    @Override
    public Container findByContainerCode(String containerCode) {
        return containerPOTransfer.toContainer(containerMapper.findByContainerCode(containerCode));
    }

    @Override
    public void save(Container container) {
        containerMapper.save(containerPOTransfer.toPO(container));
    }
}
