package com.swms.stock.infrastructure.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.swms.stock.infrastructure.persistence.mapper.ContainerStockMapper;
import com.swms.stock.infrastructure.persistence.po.ContainerStockPO;
import com.swms.stock.infrastructure.persistence.transfer.ContainerStockPOTransfer;
import com.swms.stock.domain.entity.ContainerStock;
import com.swms.stock.domain.repository.ContainerStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContainerStockRepositoryImpl implements ContainerStockRepository {

    @Autowired
    private ContainerStockMapper containerStockMapper;

    @Autowired
    private ContainerStockPOTransfer containerStockTransfer;

    @Override
    public void save(ContainerStock containerStock) {
        containerStockMapper.insert(containerStockTransfer.toContainerStockPO(containerStock));
    }

    @Override
    public void saveAll(List<ContainerStock> containerStocks) {
    }

    @Override
    public ContainerStock findById(Long stockId) {
        ContainerStockPO containerStockPO = containerStockMapper.selectById(stockId);
        return containerStockTransfer.toContainerStock(containerStockPO);
    }

    @Override
    public List<ContainerStock> findAllByIds(List<Long> containerStockIds) {
        List<ContainerStockPO> containerStockPOS = containerStockMapper.selectBatchIds(containerStockIds);
        return containerStockTransfer.toContainerStocks(containerStockPOS);
    }

    @Override
    public ContainerStock findByContainerAndSlotAndBatchAttribute(String targetContainerCode, String targetContainerSlotCode, Long skuBatchAttributeId) {
        LambdaQueryWrapper<ContainerStockPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ContainerStockPO::getContainerCode, targetContainerCode);
        wrapper.eq(ContainerStockPO::getContainerSlotCode, targetContainerSlotCode);
        wrapper.eq(ContainerStockPO::getSkuBatchAttributeId, skuBatchAttributeId);
        ContainerStockPO containerStockPO = containerStockMapper.selectOne(wrapper);
        return containerStockTransfer.toContainerStock(containerStockPO);
    }
}
