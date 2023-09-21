package com.swms.outbound.infrastructure.persistence.mapper;

import com.swms.outbound.infrastructure.persistence.po.PickingOrderPO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface PickingOrderPORepository extends JpaRepository<PickingOrderPO, Long> {

    List<PickingOrderPO> findAllByPickingOrderNoIn(Collection<String> pickingOrderNos);

    List<PickingOrderPO> findAllByWaveNoIn(Collection<String> waveNos);
}
