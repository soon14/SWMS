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

    @Mapping(source = "inboundPlanOrderDetailDTO.id", target = "inboundPlanOrderDetailId")
    @Mapping(source = "inboundPlanOrderDetailDTO.boxNo", target = "boxNo")
    @Mapping(source = "inboundPlanOrderDetailDTO.skuCode", target = "skuCode")
    @Mapping(source = "acceptRecordDTO.batchAttributes", target = "batchAttributes")
    @Mapping(source = "acceptRecordDTO.qtyAccepted", target = "qtyAccepted")
    AcceptOrderDetailDTO toDetailDO(InboundPlanOrderDetailDTO inboundPlanOrderDetailDTO, AcceptRecordDTO acceptRecordDTO);

    @Mapping(source = "inboundPlanOrder.warehouseCode", target = "warehouseCode")
    @Mapping(source = "inboundPlanOrder.id", target = "inboundPlanOrderId")
    @Mapping(ignore = true, source = "inboundPlanOrder.version", target = "version")
    AcceptOrder toDO(InboundPlanOrder inboundPlanOrder, AcceptRecordDTO acceptRecordDTO,
                     List<AcceptOrderDetailDTO> acceptOrderDetails
    );
}
