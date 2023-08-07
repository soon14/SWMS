package com.swms.inbound.infrastructure.persistence.mapper;

import com.swms.inbound.infrastructure.persistence.po.AcceptOrderDetailPO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcceptOrderDetailPORepository extends JpaRepository<AcceptOrderDetailPO, Long> {
}
