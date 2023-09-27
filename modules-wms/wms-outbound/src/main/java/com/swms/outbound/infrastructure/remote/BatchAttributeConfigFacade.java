package com.swms.outbound.infrastructure.remote;

import com.swms.mdm.api.config.IBatchAttributeConfigApi;
import com.swms.mdm.api.config.dto.BatchAttributeConfigDTO;
import lombok.Setter;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

@Service
@Setter
public class BatchAttributeConfigFacade {

    @DubboReference
    private IBatchAttributeConfigApi skuBatchAttributeConfigApi;

    public BatchAttributeConfigDTO getByOwnerAndSkuFirstCategory(String ownerCode, String skuFirstCategory) {
        return skuBatchAttributeConfigApi.getByOwnerAndSkuFirstCategory(ownerCode, skuFirstCategory);
    }
}
