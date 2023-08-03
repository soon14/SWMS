package com.swms.wms.basic.container;

import com.swms.common.utils.utils.ObjectUtils;
import com.swms.wms.BaseTest;
import com.swms.wms.api.basic.IContainerSpecApi;
import com.swms.wms.api.basic.dto.ContainerSpecDTO;
import com.swms.wms.basic.container.domain.entity.ContainerSpec;
import com.swms.wms.basic.container.domain.repository.ContainerSpecRepository;
import com.swms.wms.basic.container.domain.transfer.ContainerSpecTransfer;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

class ContainerSpecApiTest extends BaseTest {

    @Autowired
    private IContainerSpecApi containerSpecApi;

    @Autowired
    private ContainerSpecRepository containerSpecRepository;

    @Autowired
    private ContainerSpecTransfer containerSpecTransfer;

    final String warehouseCode = "123";

    @Test
    @Transactional
    void testSave() {
        ContainerSpecDTO containerSpecDTO = ObjectUtils.getRandomObject(ContainerSpecDTO.class);
        containerSpecDTO.setContainerSpecCode("test");
        Assertions.assertDoesNotThrow(() -> containerSpecApi.save(containerSpecDTO));
        ContainerSpec containerSpec = containerSpecRepository.findByContainerSpecCode("test", warehouseCode);
        Assertions.assertNotNull(containerSpec);

    }

    @Test
    void testUpdate() {
        ContainerSpec containerSpec = containerSpecRepository.findByContainerSpecCode("test", warehouseCode);
        ContainerSpecDTO.ContainerSlotSpec containerSlotSpec = ObjectUtils.getRandomObject(ContainerSpecDTO.ContainerSlotSpec.class);
        containerSpec.getContainerSlotSpecs().get(0).setChildren(Lists.newArrayList(containerSlotSpec));
        containerSpecApi.save(containerSpecTransfer.toDTO(containerSpec));
        containerSpec = containerSpecRepository.findByContainerSpecCode("test", warehouseCode);
        Assertions.assertEquals(2, containerSpec.getContainerSlotSpecs().size());
    }
}
