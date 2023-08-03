package com.swms.wms.stock.application;

import com.swms.wms.api.stock.ISkuBatchAttributeApi;
import com.swms.wms.api.stock.dto.SkuBatchAttributeDTO;
import com.swms.wms.stock.domain.entity.SkuBatchAttribute;
import com.swms.wms.stock.domain.entity.SkuBatchStock;
import com.swms.wms.stock.domain.repository.SkuBatchAttributeRepository;
import com.swms.wms.stock.domain.repository.SkuBatchStockRepository;
import com.swms.wms.stock.domain.transfer.SkuBatchAttributeTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Validated
public class SkuAttributeApiImpl implements ISkuBatchAttributeApi {

    @Autowired
    private SkuBatchAttributeRepository skuBatchAttributeRepository;

    @Autowired
    private SkuBatchStockRepository skuBatchStockRepository;

    @Autowired
    private SkuBatchAttributeTransfer skuBatchAttributeTransfer;

    @Override
    public List<SkuBatchAttributeDTO> getBySkuBatchStockIds(Collection<Long> skuBatchStockIds) {
        List<SkuBatchStock> skuBatchStocks = skuBatchStockRepository.findAllByIds(skuBatchStockIds);

        List<SkuBatchAttribute> skuBatchAttributes = skuBatchAttributeRepository
            .findAllByIds(skuBatchStocks.stream().map(SkuBatchStock::getSkuBatchAttributeId).toList());

        Map<Long, List<SkuBatchStock>> groupMap = skuBatchStocks.stream()
            .collect(Collectors.groupingBy(SkuBatchStock::getSkuBatchAttributeId));
        List<SkuBatchAttributeDTO> skuBatchAttributeDTOS = skuBatchAttributeTransfer.toDTOS(skuBatchAttributes);
        skuBatchAttributeDTOS.forEach(v ->
            v.setSkuBatchStockIds(groupMap.get(v.getId()).stream().map(SkuBatchStock::getId).toList()));

        return skuBatchAttributeTransfer.toDTOS(skuBatchAttributes);
    }
}
