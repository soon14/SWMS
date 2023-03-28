package com.swms.wms.warehouse.container.domain.aggregate;

import com.google.common.collect.Lists;
import com.swms.utils.utils.BeanHelper;
import com.swms.wms.api.warehouse.dto.ContainerLayoutDTO;
import com.swms.wms.warehouse.container.domain.entity.Container;
import com.swms.wms.warehouse.container.domain.entity.ContainerSlotSpec;
import com.swms.wms.warehouse.container.domain.entity.ContainerSpec;
import com.swms.wms.warehouse.container.domain.repository.ContainerRepository;
import com.swms.wms.warehouse.container.domain.repository.ContainerSlotRepository;
import com.swms.wms.warehouse.container.domain.repository.ContainerSpecRepository;
import com.swms.wms.warehouse.container.domain.repository.ContainerSpecSlotRepository;
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
    private ContainerSlotRepository containerSlotRepository;

    @Autowired
    private ContainerSpecRepository containerSpecRepository;

    @Autowired
    private ContainerSpecSlotRepository containerSpecSlotRepository;

    public ContainerLayoutDTO queryContainerLayout(String containerCode, String face) {
        Container container = containerRepository.findByContainerCode(containerCode);
        ContainerSpec containerSpec = containerSpecRepository.findByContainerSpecCode(container.getContainerSpecCode());
        List<ContainerSlotSpec> containerSlotSpecs = containerSpecSlotRepository.findAllByContainerSpecCodeAndFace(containerSpec.getContainerSpecCode(), face);
        ContainerLayoutDTO containerLayoutDTO = buildContainerLayout(containerSpec, containerSlotSpecs);
        containerLayoutDTO.setContainerCode(containerCode);
        return containerLayoutDTO;
    }

    private ContainerLayoutDTO buildContainerLayout(ContainerSpec containerSpec, List<ContainerSlotSpec> containerSlotSpecs) {
        ContainerLayoutDTO containerLayoutDTO = new ContainerLayoutDTO();
        BeanHelper.copyNonNullProperties(containerSpec, containerLayoutDTO);
        List<ContainerLayoutDTO.ContainerSlotLayoutDTO> containerSlotSpecTree = buildContainerSlotSpecTree(containerSlotSpecs);
        containerLayoutDTO.setContainerSlotLayoutDTOS(containerSlotSpecTree);
        return containerLayoutDTO;
    }

    private List<ContainerLayoutDTO.ContainerSlotLayoutDTO> buildContainerSlotSpecTree(List<ContainerSlotSpec> containerSlotSpecs) {

        List<ContainerLayoutDTO.ContainerSlotLayoutDTO> newContainerSlotLayoutDTOS = Lists.newArrayList();

        List<ContainerLayoutDTO.ContainerSlotLayoutDTO> subContainerDTOS = containerSlotSpecs.stream().map(containerSlotSpec -> {
            ContainerLayoutDTO.ContainerSlotLayoutDTO containerSlotLayoutDTO = new ContainerLayoutDTO.ContainerSlotLayoutDTO();
            BeanHelper.copyNonNullProperties(containerSlotSpec, containerSlotLayoutDTO);
            return containerSlotLayoutDTO;
        }).collect(Collectors.toList());

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
