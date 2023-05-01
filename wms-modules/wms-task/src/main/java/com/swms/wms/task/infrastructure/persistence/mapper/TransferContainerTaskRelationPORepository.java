package com.swms.wms.task.infrastructure.persistence.mapper;

import com.swms.wms.task.infrastructure.persistence.po.TransferContainerTaskRelationPO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferContainerTaskRelationPORepository extends JpaRepository<TransferContainerTaskRelationPO, Long> {
}
