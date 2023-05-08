package com.swms.wms.stock.application;

import com.swms.mdm.api.main.data.dto.SkuMainDataDTO;
import com.swms.wms.api.stock.IStockApi;
import com.swms.wms.api.stock.dto.ContainerStockDTO;
import com.swms.wms.api.stock.dto.ContainerStockLockDTO;
import com.swms.wms.api.stock.dto.SkuBatchStockLockDTO;
import com.swms.wms.api.stock.dto.StockTransferDTO;
import com.swms.wms.stock.domain.entity.ContainerStock;
import com.swms.wms.stock.domain.entity.SkuBatchAttribute;
import com.swms.wms.stock.domain.entity.SkuBatchStock;
import com.swms.wms.stock.domain.repository.ContainerStockRepository;
import com.swms.wms.stock.domain.repository.SkuBatchAttributeRepository;
import com.swms.wms.stock.domain.repository.SkuBatchStockRepository;
import com.swms.wms.stock.domain.service.StockTransferService;
import com.swms.wms.stock.domain.transfer.ContainerStockTransfer;
import com.swms.wms.stock.domain.transfer.SkuBatchAttributeTransfer;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.TreeMap;

@Service
public class StockApplicationApiImpl implements IStockApi {

    @Autowired
    private StockTransferService stockTransferService;

    @Autowired
    private ContainerStockRepository containerStockRepository;

    @Autowired
    private ContainerStockTransfer containerStockTransfer;

    @Autowired
    private SkuBatchStockRepository skuBatchStockRepository;

    @Autowired
    private SkuBatchAttributeRepository skuBatchAttributeRepository;

    @Override
    public void createSkuBatchAttribute(SkuMainDataDTO skuMainDataDTO, TreeMap<String, Object> skuAttributes) {
        List<SkuBatchAttribute> skuBatchAttributes = skuBatchAttributeRepository.findAllBySkuId(skuMainDataDTO.getId());
        if (CollectionUtils.isEmpty(skuBatchAttributes) || skuBatchAttributes.stream().noneMatch(v -> v.isSame(skuAttributes))) {
            skuBatchAttributeRepository.save(new SkuBatchAttribute(skuMainDataDTO, skuAttributes));
        }
    }

    @Override
    public void createStock(List<StockTransferDTO> stockTransferDTOS) {
        stockTransferService.createStock(stockTransferDTOS);
    }

    @Override
    public void lockSkuBatchStock(List<SkuBatchStockLockDTO> skuBatchStockLockDTOS) {
        List<Long> skuBatchStockIds = skuBatchStockLockDTOS.stream().map(SkuBatchStockLockDTO::getSkuBatchStockId).toList();
        List<SkuBatchStock> skuBatchStocks = skuBatchStockRepository.findAllByIds(skuBatchStockIds);

        skuBatchStocks.forEach(skuBatchStock -> skuBatchStockLockDTOS.forEach(skuBatchStockLockDTO -> {
            if (Objects.equals(skuBatchStock.getId(), skuBatchStockLockDTO.getSkuBatchStockId())) {
                skuBatchStock.lockQty(skuBatchStockLockDTO.getLockQty(), skuBatchStockLockDTO.getLockType());
            }
        }));
        skuBatchStockRepository.saveAll(skuBatchStocks);
    }

    @Override
    public void lockContainerStock(List<ContainerStockLockDTO> containerStockLockDTOS) {
        List<Long> containerStockIds = containerStockLockDTOS.stream().map(ContainerStockLockDTO::getContainerStockId).toList();
        List<ContainerStock> containerStocks = containerStockRepository.findAllByIds(containerStockIds);

        containerStocks.forEach(containerStock -> containerStockLockDTOS.forEach(containerStockLockDTO -> {
            if (Objects.equals(containerStock.getId(), containerStockLockDTO.getContainerStockId())) {
                containerStock.lockQty(containerStockLockDTO.getLockQty(), containerStockLockDTO.getLockType());
            }
        }));
        containerStockRepository.saveAll(containerStocks);
    }

    @Override
    public List<ContainerStockDTO> getContainerStock(String containerCode) {
        return containerStockTransfer.toDTOs(containerStockRepository.findAllByContainerCode(containerCode));
    }
}
