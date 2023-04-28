package com.swms.wms.basic.container.infrastructure.persistence.mapper;

import com.swms.wms.basic.container.infrastructure.persistence.po.ContainerPO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContainerMapper extends JpaRepository<ContainerPO, String> {
    ContainerPO findByContainerCode(String containerCode);
}
