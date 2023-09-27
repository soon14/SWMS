package com.swms.wms.inbound.infrastructure.persistence.mapper;

import com.swms.wms.inbound.infrastructure.persistence.po.ReceiveOrderDetailPO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiveOrderDetailPORepository extends JpaRepository<ReceiveOrderDetailPO, Long> {
}
