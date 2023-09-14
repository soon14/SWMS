package com.swms.wms.api.stock.dto;

import com.swms.mdm.api.config.dto.BatchAttributeConfigDTO;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

@Data
public class SkuBatchAttributeDTO {

    private Long id;

    private Long skuId;

    private TreeMap<String, Object> skuAttributes;

    private String batchNo;

    /**
     * for task query to mapping
     */
    private List<Long> skuBatchStockIds;

    public boolean match(BatchAttributeConfigDTO batchAttributeConfigDTO, Map<String, Object> batchAttributes) {

        if (batchAttributes == null) {
            return true;
        }

        return batchAttributeConfigDTO.getBatchAttributeFieldConfigs().stream().allMatch(config -> {
            if (!config.isEnable()) {
                return true;
            }
            Object inputValue = batchAttributes.getOrDefault(config.getFieldCode(), "");
            Object existValue = skuAttributes.getOrDefault(config.getFieldCode(), "");
            return Objects.equals(inputValue, existValue);
        });
    }
}
