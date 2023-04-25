package com.swms.wms.basic.container;

import com.swms.utils.utils.ObjectUtils;
import com.swms.wms.BaseTest;
import com.swms.wms.api.basic.IContainerSpecApi;
import com.swms.wms.api.basic.dto.ContainerSpecDTO;
import com.swms.wms.basic.container.domain.entity.ContainerSpec;
import com.swms.wms.basic.container.domain.repository.ContainerSpecRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ContainerSpecApiTest extends BaseTest {

    @Autowired
    private IContainerSpecApi containerSpecApi;

    @Autowired
    private ContainerSpecRepository containerSpecRepository;

    @Test
    void testCreateContainerSpec() {
        ContainerSpecDTO containerSpecDTO = ObjectUtils.getRandomObject(ContainerSpecDTO.class);
        containerSpecDTO.setContainerSpecCode("test");
        Assertions.assertDoesNotThrow(() -> containerSpecApi.save(containerSpecDTO));
        ContainerSpec containerSpec = containerSpecRepository.findByContainerSpecCode("test");
        Assertions.assertNotNull(containerSpec);
    }

    @Test
    void testCreateContainerSpecError() {
        ContainerSpecDTO containerSpecDTO = ObjectUtils.getRandomObject(ContainerSpecDTO.class);
        containerSpecDTO.setContainerSpecCode("test");
        containerSpecDTO.setLength(0);
        containerSpecDTO.getContainerSlotSpecs().addAll(containerSpecDTO.getContainerSlotSpecs());
        Assertions.assertThrows(Exception.class, () -> containerSpecApi.save(containerSpecDTO));
        ContainerSpec containerSpec = containerSpecRepository.findByContainerSpecCode("test");
        Assertions.assertNotNull(containerSpec);
    }
}
