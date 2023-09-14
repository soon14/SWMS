package com.swms.wms.api.stock;

import com.swms.wms.api.stock.dto.SkuBatchAttributeDTO;
import com.swms.wms.api.stock.dto.SkuBatchAttributeMatchRequest;
import com.swms.wms.api.stock.dto.SkuBatchAttributeMatchResult;
import jakarta.validation.constraints.NotEmpty;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ISkuBatchAttributeApi {

    List<SkuBatchAttributeDTO> getBySkuBatchStockIds(@NotEmpty Collection<Long> skuBatchStockIds);

    SkuBatchAttributeDTO getOrCreateSkuBatchAttribute(Long skuId, Map<String, Object> batchAttributes);

    List<SkuBatchAttributeDTO> getBySkuIds(@NotEmpty Collection<Long> skuIds);
}
