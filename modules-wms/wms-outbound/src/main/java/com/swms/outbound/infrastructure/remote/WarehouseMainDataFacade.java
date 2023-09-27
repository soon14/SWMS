package com.swms.outbound.infrastructure.remote;

import com.swms.mdm.api.main.data.IWarehouseMainDataApi;
import com.swms.mdm.api.main.data.dto.WarehouseMainDataDTO;
import lombok.Setter;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Setter
public class WarehouseMainDataFacade {

    @DubboReference
    protected IWarehouseMainDataApi iWarehouseMainDataApi;

    public Collection<WarehouseMainDataDTO> getWarehouses(Collection<String> warehouseCodes) {
        return iWarehouseMainDataApi.getWarehouses(warehouseCodes);
    }
}
