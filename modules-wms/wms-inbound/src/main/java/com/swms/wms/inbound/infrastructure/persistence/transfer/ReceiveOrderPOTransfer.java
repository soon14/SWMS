package com.swms.wms.inbound.infrastructure.persistence.transfer;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import com.swms.wms.inbound.domain.entity.ReceiveOrder;
import com.swms.wms.inbound.infrastructure.persistence.po.ReceiveOrderDetailPO;
import com.swms.wms.inbound.infrastructure.persistence.po.ReceiveOrderPO;
import com.swms.wms.api.inbound.dto.ReceiveOrderDetailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
    nullValueCheckStrategy = ALWAYS,
    nullValueMappingStrategy = RETURN_NULL,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ReceiveOrderPOTransfer {
    ReceiveOrderPO toPO(ReceiveOrder receiveOrder);

    List<ReceiveOrderDetailPO> toDetailPOs(List<ReceiveOrderDetailDTO> receiveOrderDetails);
}
