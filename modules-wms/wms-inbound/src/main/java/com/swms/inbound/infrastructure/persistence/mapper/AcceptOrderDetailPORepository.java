package com.swms.inbound.infrastructure.persistence.mapper;

import com.swms.inbound.infrastructure.persistence.po.AcceptOrderDetailPO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AcceptOrderDetailPORepository extends JpaRepository<AcceptOrderDetailPO, Long> {

    List<AcceptOrderDetailPO> findByAcceptOrderId(Long acceptOrderId);
}
