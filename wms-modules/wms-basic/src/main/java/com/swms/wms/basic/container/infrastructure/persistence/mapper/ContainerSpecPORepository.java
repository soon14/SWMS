package com.swms.wms.basic.container.infrastructure.persistence.mapper;

import com.swms.wms.basic.container.infrastructure.persistence.po.ContainerSpecPO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContainerSpecPORepository extends JpaRepository<ContainerSpecPO, String> {
    ContainerSpecPO findByContainerSpecCode(String containerSpecCode);
}
