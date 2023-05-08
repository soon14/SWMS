package com.swms.wms.stock.infrastructure.repository.impl;

import com.swms.wms.stock.domain.entity.ContainerStock;
import com.swms.wms.stock.domain.repository.ContainerStockRepository;
import com.swms.wms.stock.infrastructure.persistence.mapper.ContainerStockPORepository;
import com.swms.wms.stock.infrastructure.persistence.po.ContainerStockPO;
import com.swms.wms.stock.infrastructure.persistence.transfer.ContainerStockPOTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContainerStockRepositoryImpl implements ContainerStockRepository {

    @Autowired
    private ContainerStockPORepository containerStockPORepository;

    @Autowired
    private ContainerStockPOTransfer containerStockPOTransfer;

    @Override
    public void save(ContainerStock containerStock) {
        containerStockPORepository.save(containerStockPOTransfer.toPO(containerStock));
    }

    @Override
    public void saveAll(List<ContainerStock> containerStocks) {
        containerStockPORepository.saveAll(containerStockPOTransfer.toPOs(containerStocks));
    }

    @Override
    public ContainerStock findById(Long stockId) {
        ContainerStockPO containerStockPO = containerStockPORepository.findById(stockId).orElseThrow();
        return containerStockPOTransfer.toDO(containerStockPO);
    }

    @Override
    public List<ContainerStock> findAllByIds(List<Long> containerStockIds) {
        List<ContainerStockPO> containerStockPOS = containerStockPORepository.findAllById(containerStockIds);
        return containerStockPOTransfer.toDOs(containerStockPOS);
    }

    @Override
    public ContainerStock findByContainerAndSlotAndBatchAttribute(String targetContainerCode, String targetContainerSlotCode, Long skuBatchAttributeId) {
        ContainerStockPO containerStockPO = containerStockPORepository
            .findBySkuBatchAttributeIdAndContainerCodeAndContainerSlotCode(skuBatchAttributeId, targetContainerCode, targetContainerSlotCode);
        return containerStockPOTransfer.toDO(containerStockPO);
    }

    @Override
    public List<ContainerStock> findAllByContainerCode(String containerCode) {
        return containerStockPOTransfer.toDOs(containerStockPORepository.findAllByContainerCode(containerCode));
    }
}
