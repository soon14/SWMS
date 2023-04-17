package com.swms.stock.Infrastructure.repository.impl;

import com.swms.stock.domain.entity.ContainerStock;
import com.swms.stock.domain.repository.ContainerStockRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContainerStockRepositoryImpl implements ContainerStockRepository {

    @Override
    public void save(ContainerStock containerStock) {

    }

    @Override
    public void saveAll(List<ContainerStock> containerStocks) {

    }

    @Override
    public ContainerStock findById(Long stockId) {
        return null;
    }

    @Override
    public List<ContainerStock> findAllByIds(List<Long> containerStockIds) {
        return null;
    }

    @Override
    public ContainerStock existsByContainerCodeAndContainerSlotCodeAndSkuBatchAttributeId(String targetContainerCode, String targetContainerSlotCode, Long skuBatchAttributeId) {
        return null;
    }
}
