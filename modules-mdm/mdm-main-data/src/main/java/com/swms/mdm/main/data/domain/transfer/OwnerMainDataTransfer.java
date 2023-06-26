package com.swms.mdm.main.data.domain.transfer;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import com.swms.mdm.api.main.data.dto.OwnerMainDataDTO;
import com.swms.mdm.api.main.data.dto.SkuMainDataDTO;
import com.swms.mdm.main.data.controller.parameter.OwnerMainDataVO;
import com.swms.mdm.main.data.domain.entity.OwnerMainData;
import com.swms.mdm.main.data.domain.entity.SkuMainData;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
    nullValueCheckStrategy = ALWAYS,
    nullValueMappingStrategy = RETURN_NULL,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OwnerMainDataTransfer {
    OwnerMainData toOwnerMainData(OwnerMainDataDTO ownerMainDataDTO);

    OwnerMainDataDTO toOwnerMainDataDTO(OwnerMainData ownerMainData);

}
