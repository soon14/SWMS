package com.swms.wms.stock.application;

import com.swms.mdm.api.main.data.dto.SkuMainDataDTO;
import com.swms.wms.api.stock.IStockApi;
import com.swms.wms.api.stock.dto.ContainerStockDTO;
import com.swms.wms.api.stock.dto.ContainerStockLockDTO;
import com.swms.wms.api.stock.dto.SkuBatchStockDTO;
import com.swms.wms.api.stock.dto.SkuBatchStockLockDTO;
import com.swms.wms.stock.domain.entity.ContainerStock;
import com.swms.wms.stock.domain.entity.SkuBatchAttribute;
import com.swms.wms.stock.domain.entity.SkuBatchStock;
import com.swms.wms.stock.domain.repository.ContainerStockRepository;
import com.swms.wms.stock.domain.repository.SkuBatchAttributeRepository;
import com.swms.wms.stock.domain.repository.SkuBatchStockRepository;
import com.swms.wms.stock.domain.transfer.ContainerStockTransfer;
import com.swms.wms.stock.domain.transfer.SkuBatchStockTransfer;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class StockApplicationApiImpl implements IStockApi {

    @Autowired
    private ContainerStockRepository containerStockRepository;

    @Autowired
    private ContainerStockTransfer containerStockTransfer;

    @Autowired
    private SkuBatchStockRepository skuBatchStockRepository;

    @Autowired
    private SkuBatchStockTransfer skuBatchStockTransfer;

    @Autowired
    private SkuBatchAttributeRepository skuBatchAttributeRepository;

    @Override
    public void createSkuBatchAttribute(SkuMainDataDTO skuMainDataDTO, Map<String, Object> skuAttributes) {
        List<SkuBatchAttribute> skuBatchAttributes = skuBatchAttributeRepository.findAllBySkuId(skuMainDataDTO.getId());
        if (CollectionUtils.isEmpty(skuBatchAttributes) || skuBatchAttributes.stream().noneMatch(v -> v.isSame(skuAttributes))) {
            skuBatchAttributeRepository.save(new SkuBatchAttribute(skuMainDataDTO.getId(), skuAttributes));
        }
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
    public void freezeContainerStock(Long id, int qty) {
        ContainerStock containerStock = containerStockRepository.findById(id);
        containerStock.freezeQty(qty);
        containerStockRepository.save(containerStock);
    }

    @Override
    public void unFreezeContainerStock(Long id, int qty) {
        ContainerStock containerStock = containerStockRepository.findById(id);
        containerStock.unFreezeQty(qty);
        containerStockRepository.save(containerStock);
    }

    @Override
    public List<SkuBatchStockDTO> getBySkuBatchAttributeIds(Collection<Long> skuBatchAttributeIds) {
        List<SkuBatchStock> skuBatchStocks = skuBatchStockRepository.findAllBySkuBatchAttributeIds(skuBatchAttributeIds);
        return skuBatchStockTransfer.toDTOs(skuBatchStocks);
    }

    @Override
    public List<ContainerStockDTO> getContainerStock(String containerCode) {
        return containerStockTransfer.toDTOs(containerStockRepository.findAllByContainerCode(containerCode));
    }

    @Override
    public List<ContainerStockDTO> getContainerStockBySkuBatchStockIds(List<Long> skuBatchStockIds) {
        return containerStockTransfer.toDTOs(containerStockRepository.findAllBySkuBatchStockIds(skuBatchStockIds));
    }
}
