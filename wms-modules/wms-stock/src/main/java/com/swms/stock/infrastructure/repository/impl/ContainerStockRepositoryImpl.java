package com.swms.stock.infrastructure.repository.impl;

import com.swms.stock.domain.entity.ContainerStock;
import com.swms.stock.domain.repository.ContainerStockRepository;
import com.swms.stock.infrastructure.persistence.mapper.ContainerStockPORepository;
import com.swms.stock.infrastructure.persistence.po.ContainerStockPO;
import com.swms.stock.infrastructure.persistence.transfer.ContainerStockPOTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContainerStockRepositoryImpl implements ContainerStockRepository {

    @Autowired
    private ContainerStockPORepository containerStockMapper;

    @Autowired
    private ContainerStockPOTransfer containerStockTransfer;

    @Override
    public void save(ContainerStock containerStock) {
        containerStockMapper.save(containerStockTransfer.toPO(containerStock));
    }

    @Override
    public void saveAll(List<ContainerStock> containerStocks) {
        containerStockMapper.saveAll(containerStockTransfer.toPOs(containerStocks));
    }

    @Override
    public ContainerStock findById(Long stockId) {
        ContainerStockPO containerStockPO = containerStockMapper.findById(stockId).orElseThrow();
        return containerStockTransfer.toDO(containerStockPO);
    }

    @Override
    public List<ContainerStock> findAllByIds(List<Long> containerStockIds) {
        List<ContainerStockPO> containerStockPOS = containerStockMapper.findAllById(containerStockIds);
        return containerStockTransfer.toDOs(containerStockPOS);
    }

    @Override
    public ContainerStock findByContainerAndSlotAndBatchAttribute(String targetContainerCode, String targetContainerSlotCode, Long skuBatchAttributeId) {
        ContainerStockPO containerStockPO = containerStockMapper
            .findBySkuBatchAttributeIdAndContainerCodeAndContainerSlotCode(skuBatchAttributeId, targetContainerCode, targetContainerSlotCode);
        return containerStockTransfer.toDO(containerStockPO);
    }
}
