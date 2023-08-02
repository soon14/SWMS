package com.swms.wms.stock.domain.entity;

import com.swms.mdm.api.main.data.dto.SkuMainDataDTO;
import com.swms.common.utils.base.UpdateUserDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.SortedMap;

@EqualsAndHashCode(callSuper = true)
@Data
public class SkuBatchAttribute extends UpdateUserDTO {

    private Long id;
    private SkuMainDataDTO skuMainData;
    private SortedMap<String, Object> skuAttributes;
    private String batchNo;

    private Long version;

    public SkuBatchAttribute(SkuMainDataDTO skuMainData, SortedMap<String, Object> skuAttributes) {
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

    public String getBatchNo() {
        if (StringUtils.isEmpty(this.batchNo)) {
            return this.batchNo;
        }

        if (MapUtils.isEmpty(this.skuAttributes)) {
            return "";
        }

        return DigestUtils.md5DigestAsHex(this.skuAttributes.toString().getBytes(StandardCharsets.UTF_8));
    }

}
