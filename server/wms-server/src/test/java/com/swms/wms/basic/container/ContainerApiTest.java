package com.swms.wms.basic.container;

import com.swms.wms.BaseTest;
import com.swms.wms.api.basic.IContainerApi;
import com.swms.wms.basic.container.domain.entity.Container;
import com.swms.wms.basic.container.domain.repository.ContainerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ContainerApiTest extends BaseTest {

    @Autowired
    private IContainerApi containerApi;

    @Autowired
    private ContainerRepository containerRepository;

    @Test
    void testCreateContainer() {
        containerApi.createContainer("containerCode1", "test");
        Container container = containerRepository.findByContainerCode("containerCode1");
        Assertions.assertNotNull(container);
    }
}
