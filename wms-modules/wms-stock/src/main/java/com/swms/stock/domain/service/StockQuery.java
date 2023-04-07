package com.swms.stock.domain.service;

import com.swms.stock.domain.entity.ContainerStock;
import com.swms.stock.domain.repository.ContainerStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockQuery {

    @Autowired
    private ContainerStockRepository containerStockRepository;

    public ContainerStock queryByContainerStockId(Long containerStockId) {
        return containerStockRepository.findById(containerStockId);
    }
}
