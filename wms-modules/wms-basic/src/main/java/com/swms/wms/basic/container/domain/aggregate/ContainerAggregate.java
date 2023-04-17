package com.swms.wms.basic.container.domain.aggregate;

import com.google.common.collect.Lists;
import com.swms.wms.api.basic.dto.ContainerLayoutDTO;
import com.swms.wms.basic.container.domain.entity.Container;
import com.swms.wms.basic.container.domain.entity.ContainerSpec;
import com.swms.wms.basic.container.domain.repository.ContainerRepository;
import com.swms.wms.basic.container.domain.repository.ContainerSpecRepository;
import com.swms.wms.basic.container.domain.transfer.ContainerSpecTransfer;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ContainerAggregate {

    @Autowired
    private ContainerRepository containerRepository;

    @Autowired
    private ContainerSpecRepository containerSpecRepository;

    @Autowired
    private ContainerSpecTransfer containerSpecTransfer;

    public ContainerLayoutDTO queryContainerLayout(String containerCode, String face) {
        Container container = containerRepository.findByContainerCode(containerCode);
        ContainerSpec containerSpec = containerSpecRepository.findByContainerSpecCode(container.getContainerSpecCode());
        List<ContainerSpec.ContainerSlotSpec> containerSlotSpecs = containerSpec.getContainerSlotSpecsByFace(face);
        ContainerLayoutDTO containerLayoutDTO = buildContainerLayout(containerSpec, containerSlotSpecs);
        containerLayoutDTO.setContainerCode(containerCode);
        return containerLayoutDTO;
    }

    private ContainerLayoutDTO buildContainerLayout(ContainerSpec containerSpec, List<ContainerSpec.ContainerSlotSpec> containerSlotSpecs) {
        ContainerLayoutDTO containerLayoutDTO = containerSpecTransfer.toContainerLayoutDTO(containerSpec);
        List<ContainerLayoutDTO.ContainerSlotLayoutDTO> containerSlotSpecTree = buildContainerSlotSpecTree(containerSlotSpecs);
        containerLayoutDTO.setContainerSlotLayoutDTOS(containerSlotSpecTree);
        return containerLayoutDTO;
    }

    private List<ContainerLayoutDTO.ContainerSlotLayoutDTO> buildContainerSlotSpecTree(List<ContainerSpec.ContainerSlotSpec> containerSlotSpecs) {

        List<ContainerLayoutDTO.ContainerSlotLayoutDTO> newContainerSlotLayoutDTOS = Lists.newArrayList();

        List<ContainerLayoutDTO.ContainerSlotLayoutDTO> subContainerDTOS = containerSpecTransfer.toContainerSlotLayoutDTOS(containerSlotSpecs);
        Map<Long, ContainerLayoutDTO.ContainerSlotLayoutDTO> codeMap = subContainerDTOS.stream().collect(Collectors.toMap(ContainerLayoutDTO.ContainerSlotLayoutDTO::getId, Function.identity()));

        subContainerDTOS.forEach(slotLayoutDTO -> {
            if (slotLayoutDTO.getParentId() > 0) {
                ContainerLayoutDTO.ContainerSlotLayoutDTO parent = codeMap.get(slotLayoutDTO.getParentId());
                if (CollectionUtils.isNotEmpty(parent.getChildren())) {
                    parent.getChildren().add(slotLayoutDTO);
                } else {
                    parent.setChildren(Lists.newArrayList(slotLayoutDTO));
                }
            } else {
                newContainerSlotLayoutDTOS.add(slotLayoutDTO);
            }
        });
        return newContainerSlotLayoutDTOS;
    }
}
