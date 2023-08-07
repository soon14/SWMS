package com.swms.inbound.domain.transfer;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import com.swms.inbound.domain.entity.AcceptOrder;
import com.swms.inbound.domain.entity.InboundPlanOrder;
import com.swms.wms.api.inbound.dto.AcceptOrderDetailDTO;
import com.swms.wms.api.inbound.dto.AcceptRecordDTO;
import com.swms.wms.api.inbound.dto.InboundPlanOrderDetailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
    nullValueCheckStrategy = ALWAYS,
    nullValueMappingStrategy = RETURN_NULL,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AcceptOrderTransfer {

    AcceptOrder toAcceptOrder(InboundPlanOrder inboundPlanOrder);


    @Mapping(source = "id", target = "inboundPlanOrderDetailId")
    @Mapping(ignore = true, source = "id", target = "id")
    AcceptOrderDetailDTO toDetailDO(InboundPlanOrderDetailDTO inboundPlanOrderDetailDTO);

    @Mapping(source = "inboundPlanOrderDetailDTOS", target = "acceptOrderDetails")
    @Mapping(ignore = true, source = "inboundPlanOrder.id", target = "id")
    @Mapping(ignore = true, source = "inboundPlanOrder.version", target = "version")
    AcceptOrder toDO(InboundPlanOrder inboundPlanOrder, List<InboundPlanOrderDetailDTO> inboundPlanOrderDetails,
                     List<AcceptRecordDTO> acceptRecordDTOS);


    AcceptOrderDetailDTO fromAcceptRecordToDTO(AcceptRecordDTO acceptRecordDTO);
}
