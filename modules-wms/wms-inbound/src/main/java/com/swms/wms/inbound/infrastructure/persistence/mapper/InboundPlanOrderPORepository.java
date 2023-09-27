package com.swms.wms.inbound.infrastructure.persistence.mapper;

import com.swms.wms.inbound.infrastructure.persistence.po.InboundPlanOrderPO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface InboundPlanOrderPORepository extends JpaRepository<InboundPlanOrderPO, Long> {

    List<InboundPlanOrderPO> findByCustomerOrderNo(String customerOrderNo);

    Optional<Collection<InboundPlanOrderPO>> findByCustomerOrderNoIn(Collection<String> customerOrderNos);
}
