package com.swms.wms.outbound.infrastructure.persistence.transfer;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import com.swms.wms.outbound.domain.entity.PickingOrder;
import com.swms.wms.outbound.infrastructure.persistence.po.PickingOrderDetailPO;
import com.swms.wms.outbound.infrastructure.persistence.po.PickingOrderPO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
    nullValueCheckStrategy = ALWAYS,
    nullValueMappingStrategy = RETURN_NULL,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PickingOrderPOTransfer {
    List<PickingOrderPO> toPOs(List<PickingOrder> pickingOrders);

    List<PickingOrder> toDOs(List<PickingOrderPO> pickingOrderPOS);

    PickingOrder toDO(PickingOrderPO pickingOrderPO, List<PickingOrderDetailPO> details);
}
