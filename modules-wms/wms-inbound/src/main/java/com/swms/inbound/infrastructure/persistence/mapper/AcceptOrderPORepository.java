package com.swms.inbound.infrastructure.persistence.mapper;

import com.swms.inbound.infrastructure.persistence.po.AcceptOrderPO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcceptOrderPORepository extends JpaRepository<AcceptOrderPO, Long> {
}
