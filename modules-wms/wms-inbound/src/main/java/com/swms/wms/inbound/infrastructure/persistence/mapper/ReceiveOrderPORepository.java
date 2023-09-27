package com.swms.wms.inbound.infrastructure.persistence.mapper;

import com.swms.wms.inbound.infrastructure.persistence.po.ReceiveOrderPO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiveOrderPORepository extends JpaRepository<ReceiveOrderPO, Long> {
}
