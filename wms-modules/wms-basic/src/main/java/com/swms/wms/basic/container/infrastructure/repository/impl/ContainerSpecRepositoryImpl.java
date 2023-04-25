package com.swms.wms.basic.container.infrastructure.repository.impl;

import com.swms.wms.basic.container.domain.entity.ContainerSpec;
import com.swms.wms.basic.container.domain.repository.ContainerSpecRepository;
import com.swms.wms.basic.container.infrastructure.persistence.mapper.ContainerSpecMapper;
import com.swms.wms.basic.container.infrastructure.persistence.transfer.ContainerSpecPOTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContainerSpecRepositoryImpl implements ContainerSpecRepository {

    @Autowired
    private ContainerSpecMapper containerSpecMapper;

    @Autowired
    private ContainerSpecPOTransfer containerSpecPOTransfer;

    @Override
    public ContainerSpec findByContainerSpecCode(String containerSpecCode) {
        return containerSpecPOTransfer.toContainerSpec(containerSpecMapper.findByContainerSpecCode(containerSpecCode));
    }

    @Override
    public void save(ContainerSpec containerSpec) {
        containerSpecMapper.save(containerSpecPOTransfer.toPO(containerSpec));
    }
}
