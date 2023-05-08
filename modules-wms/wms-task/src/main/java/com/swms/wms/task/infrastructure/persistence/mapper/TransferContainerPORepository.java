package com.swms.wms.task.infrastructure.persistence.mapper;

import com.swms.wms.task.infrastructure.persistence.po.TransferContainerPO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferContainerPORepository extends JpaRepository<TransferContainerPO, Long> {
}
