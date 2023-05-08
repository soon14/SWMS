package com.swms.wms.basic.container.application;

import com.google.common.base.Preconditions;
import com.swms.wms.api.basic.IContainerApi;
import com.swms.wms.api.basic.dto.ContainerSpecDTO;
import com.swms.wms.basic.container.domain.entity.Container;
import com.swms.wms.basic.container.domain.entity.ContainerSpec;
import com.swms.wms.basic.container.domain.repository.ContainerRepository;
import com.swms.wms.basic.container.domain.repository.ContainerSpecRepository;
import com.swms.wms.basic.container.domain.transfer.ContainerSpecTransfer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
@Service
public class ContainerApiImpl implements IContainerApi {

    @Autowired
    private ContainerRepository containerRepository;

    @Autowired
    private ContainerSpecRepository containerSpecRepository;

    @Autowired
    private ContainerSpecTransfer containerSpecTransfer;

    @Override
    public void createContainer(String containerCode, String containerSpecCode) {
        ContainerSpec containerSpec = containerSpecRepository.findByContainerSpecCode(containerSpecCode);
        Preconditions.checkState(containerSpec != null, "container spec not exist");

        Container container = new Container(containerCode, containerSpecCode,
            containerSpecTransfer.toContainerSlots(containerSpec.getContainerSlotSpecs()));
        containerRepository.save(container);
    }

    @Override
    public ContainerSpecDTO queryContainerLayout(String containerCode, String face) {
        Container container = containerRepository.findByContainerCode(containerCode);
        ContainerSpec containerSpec = containerSpecRepository.findByContainerSpecCode(container.getContainerSpecCode());
        List<ContainerSpecDTO.ContainerSlotSpec> containerSlotSpecs = containerSpec.getContainerSlotSpecsByFace(face);
        containerSpec.setContainerSlotSpecs(containerSlotSpecs);
        return containerSpecTransfer.toDTO(containerSpec);
    }

    @Override
    public void changeContainerSpec(String containerCode, String containerSpecCode) {
        Container container = containerRepository.findByContainerCode(containerCode);
        if (StringUtils.equals(container.getContainerSpecCode(), containerSpecCode)) {
            return;
        }

        ContainerSpec containerSpec = containerSpecRepository.findByContainerSpecCode(containerSpecCode);
        Preconditions.checkState(containerSpec != null, "container spec not exist");

        container.changeContainerSpec(containerSpecCode,
            containerSpecTransfer.toContainerSlots(containerSpec.getContainerSlotSpecs()));
        containerRepository.save(container);
    }
}
