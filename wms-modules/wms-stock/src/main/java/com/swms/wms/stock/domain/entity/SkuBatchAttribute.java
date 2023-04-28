package com.swms.wms.stock.domain.entity;

import com.swms.mdm.api.main.data.dto.SkuMainDataDTO;
import lombok.Data;
import org.apache.commons.collections4.MapUtils;

import java.util.Map;
import java.util.TreeMap;

@Data
public class SkuBatchAttribute {

    private Long id;
    private SkuMainDataDTO skuMainData;
    private TreeMap<String, Object> skuAttributes;

    private Long version;

    public SkuBatchAttribute(SkuMainDataDTO skuMainData, TreeMap<String, Object> skuAttributes) {
        this.skuAttributes = skuAttributes;
        this.skuMainData = skuMainData;
    }

    public boolean isSame(Map<String, Object> skuAttributes) {
        if (MapUtils.isEmpty(skuAttributes) && MapUtils.isEmpty(this.skuAttributes)) {
            return true;
        }

        if (MapUtils.isEmpty(skuAttributes) || MapUtils.isEmpty(this.skuAttributes)) {
            return false;
        }

        return skuAttributes.equals(this.skuAttributes);
    }

    public boolean match(Map<String, Object> skuAttributes) {
        if (MapUtils.isEmpty(skuAttributes)) {
            return true;
        }

        if (MapUtils.isEmpty(this.skuAttributes)) {
            return false;
        }

        return skuAttributes.entrySet().stream().allMatch(skuAttribute -> {
            if (!this.skuAttributes.containsKey(skuAttribute.getKey())) {
                return true;
            }

            Object sourceObj = this.skuAttributes.get(skuAttribute.getKey());
            Object targetObj = skuAttribute.getValue();

            return (sourceObj == null && targetObj == null)
                || ((sourceObj != null && targetObj != null)
                && this.skuAttributes.get(skuAttribute.getKey()).equals(skuAttribute.getValue()));
        });
    }

}
