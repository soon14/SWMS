package com.swms.wms.basic.container.infrastructure.repository.impl;

import com.swms.wms.basic.container.domain.entity.Container;
import com.swms.wms.basic.container.domain.repository.ContainerRepository;
import com.swms.wms.basic.container.infrastructure.persistence.mapper.ContainerPORepository;
import com.swms.wms.basic.container.infrastructure.persistence.transfer.ContainerPOTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContainerRepositoryImpl implements ContainerRepository {

    @Autowired
    private ContainerPORepository containerPORepository;

    @Autowired
    private ContainerPOTransfer containerPOTransfer;

    @Override
    public Container findByContainerCode(String containerCode, String warehouseCode) {
        return containerPOTransfer.toContainer(containerPORepository.findByContainerCodeAndWarehouseCode(containerCode, warehouseCode));
    }

    @Override
    public void save(Container container) {
        containerPORepository.save(containerPOTransfer.toPO(container));
    }

    @Override
    public void saveAll(List<Container> containers) {
        containerPORepository.saveAll(containerPOTransfer.toPOS(containers));
    }
}
