package com.swms.wms.basic.container.application;

import static com.swms.common.utils.exception.code_enum.BasicErrorDescEnum.CONTAINER_SPECIFIC_NOT_EXIST;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.swms.wms.api.basic.IContainerApi;
import com.swms.wms.api.basic.dto.ContainerSpecDTO;
import com.swms.wms.api.basic.dto.CreateContainerDTO;
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
    public void createContainer(String warehouseCode, String containerCode, String containerSpecCode) {
        ContainerSpec containerSpec = containerSpecRepository.findByContainerSpecCode(containerSpecCode, warehouseCode);
        Preconditions.checkState(containerSpec != null, CONTAINER_SPECIFIC_NOT_EXIST.getDesc());

        Container container = new Container(warehouseCode, containerCode, containerSpecCode,
            containerSpecTransfer.toContainerSlots(containerSpec.getContainerSlotSpecs()));
        containerRepository.save(container);
    }

    @Override
    public void createContainer(CreateContainerDTO createContainerDTO) {
        ContainerSpec containerSpec = containerSpecRepository
            .findByContainerSpecCode(createContainerDTO.getContainerSpecCode(), createContainerDTO.getWarehouseCode());
        Preconditions.checkState(containerSpec != null, CONTAINER_SPECIFIC_NOT_EXIST.getDesc());

        String format = "%0" + createContainerDTO.getIndexNumber() + "d";
        List<Container> containers = Lists.newArrayList();
        for (int i = createContainerDTO.getStartIndex(); i <= createContainerDTO.getCreateNumber(); i++) {
            String containerCode = createContainerDTO.getContainerCodePrefix() + String.format(format, i);
            containers.add(new Container(createContainerDTO.getWarehouseCode(), containerCode,
                createContainerDTO.getContainerSpecCode(),
                containerSpecTransfer.toContainerSlots(containerSpec.getContainerSlotSpecs())));
        }

        containerRepository.saveAll(containers);
    }

    @Override
    public ContainerSpecDTO queryContainerLayout(String containerCode, String warehouseCode, String face) {
        Container container = containerRepository.findByContainerCode(containerCode, warehouseCode);
        ContainerSpec containerSpec = containerSpecRepository
            .findByContainerSpecCode(container.getContainerSpecCode(), container.getWarehouseCode());
        List<ContainerSpecDTO.ContainerSlotSpec> containerSlotSpecs = containerSpec.getContainerSlotSpecsByFace(face);
        containerSpec.setContainerSlotSpecs(containerSlotSpecs);
        return containerSpecTransfer.toDTO(containerSpec);
    }

    @Override
    public void changeContainerSpec(String containerCode, String containerSpecCode) {
        Container container = containerRepository.findByContainerCode(containerCode, containerCode);
        if (StringUtils.equals(container.getContainerSpecCode(), containerSpecCode)) {
            return;
        }

        ContainerSpec containerSpec = containerSpecRepository
            .findByContainerSpecCode(containerSpecCode, container.getWarehouseCode());
        Preconditions.checkState(containerSpec != null, CONTAINER_SPECIFIC_NOT_EXIST.getDesc());

        container.changeContainerSpec(containerSpecCode,
            containerSpecTransfer.toContainerSlots(containerSpec.getContainerSlotSpecs()));
        containerRepository.save(container);
    }
}
