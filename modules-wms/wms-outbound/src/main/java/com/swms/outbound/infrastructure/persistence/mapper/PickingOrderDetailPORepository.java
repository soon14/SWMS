package com.swms.outbound.infrastructure.persistence.mapper;

import com.swms.outbound.infrastructure.persistence.po.PickingOrderDetailPO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PickingOrderDetailPORepository extends JpaRepository<PickingOrderDetailPO, Long> {
}
