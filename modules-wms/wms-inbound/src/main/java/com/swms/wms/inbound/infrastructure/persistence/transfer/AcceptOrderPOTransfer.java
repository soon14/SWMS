package com.swms.wms.inbound.infrastructure.persistence.transfer;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import com.swms.wms.inbound.domain.entity.AcceptOrder;
import com.swms.wms.inbound.domain.entity.AcceptOrderDetail;
import com.swms.wms.inbound.infrastructure.persistence.po.AcceptOrderDetailPO;
import com.swms.wms.inbound.infrastructure.persistence.po.AcceptOrderPO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
    nullValueCheckStrategy = ALWAYS,
    nullValueMappingStrategy = RETURN_NULL,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AcceptOrderPOTransfer {
    AcceptOrderPO toPO(AcceptOrder acceptOrder);

    List<AcceptOrderDetailPO> toDetailPOs(List<AcceptOrderDetail> acceptOrderDetails);

    List<AcceptOrder> toDOs(List<AcceptOrderPO> acceptOrderPOS);

    AcceptOrder toDO(AcceptOrderPO acceptOrderPO, List<AcceptOrderDetailPO> acceptOrderDetails);

}
