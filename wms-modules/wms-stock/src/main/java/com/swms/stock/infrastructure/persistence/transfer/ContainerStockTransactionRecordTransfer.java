package com.swms.stock.infrastructure.persistence.transfer;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import com.swms.stock.domain.entity.ContainerStockTransactionRecord;
import com.swms.stock.infrastructure.persistence.po.ContainerStockTransactionRecordPO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
    nullValueCheckStrategy = ALWAYS,
    nullValueMappingStrategy = RETURN_NULL,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ContainerStockTransactionRecordTransfer {

    ContainerStockTransactionRecordPO toContainerTransactionRecordPO(ContainerStockTransactionRecord transactionRecord);

    ContainerStockTransactionRecord toContainerTransactionRecord(ContainerStockTransactionRecordPO transactionRecordPO);
}
