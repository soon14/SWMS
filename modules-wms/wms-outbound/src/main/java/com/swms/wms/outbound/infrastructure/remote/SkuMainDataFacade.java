package com.swms.wms.outbound.infrastructure.remote;

import com.swms.mdm.api.main.data.ISkuMainDataApi;
import com.swms.mdm.api.main.data.dto.SkuMainDataDTO;
import lombok.Setter;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@Setter
public class SkuMainDataFacade {
    @DubboReference

    private ISkuMainDataApi iSkuMainDataApi;

    public List<SkuMainDataDTO> getSkuMainData(Collection<String> skuCodes) {
        return iSkuMainDataApi.getSkuMainData(skuCodes);
    }

    public List<SkuMainDataDTO> getByIds(List<Long> skuIds) {
        return iSkuMainDataApi.getByIds(skuIds);
    }
}
