package com.swms.wms.basic.container.infrastructure.repository.impl;

import com.swms.wms.basic.container.domain.entity.ContainerSpec;
import com.swms.wms.basic.container.domain.repository.ContainerSpecRepository;
import com.swms.wms.basic.container.infrastructure.persistence.mapper.ContainerSpecPORepository;
import com.swms.wms.basic.container.infrastructure.persistence.transfer.ContainerSpecPOTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContainerSpecRepositoryImpl implements ContainerSpecRepository {

    @Autowired
    private ContainerSpecPORepository containerSpecPORepository;

    @Autowired
    private ContainerSpecPOTransfer containerSpecPOTransfer;

    @Override
    public ContainerSpec findByContainerSpecCode(String containerSpecCode, String warehouseCode) {
        return containerSpecPOTransfer.toContainerSpec(containerSpecPORepository
            .findByContainerSpecCodeAndWarehouseCode(containerSpecCode, warehouseCode));
    }

    @Override
    public void save(ContainerSpec containerSpec) {
        containerSpecPORepository.save(containerSpecPOTransfer.toPO(containerSpec));
    }

    @Override
    public ContainerSpec findById(Long id) {

        return containerSpecPOTransfer.toContainerSpec(containerSpecPORepository.findById(id).orElseThrow());
    }
}
