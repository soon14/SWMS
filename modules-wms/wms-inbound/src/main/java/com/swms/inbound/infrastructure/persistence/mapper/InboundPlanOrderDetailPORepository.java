package com.swms.inbound.infrastructure.persistence.mapper;

import com.swms.inbound.infrastructure.persistence.po.InboundPlanOrderDetailPO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InboundPlanOrderDetailPORepository extends JpaRepository<InboundPlanOrderDetailPO, Long> {

    List<InboundPlanOrderDetailPO> findByInboundPlanOrderId(Long inboundPlanOrderId);

    List<InboundPlanOrderDetailPO> findByBoxNo(String boxNo);
}
