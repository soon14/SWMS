package com.swms.outbound.infrastructure.remote;

import com.swms.mdm.api.main.data.IOwnerMainDataApi;
import com.swms.mdm.api.main.data.dto.OwnerMainDataDTO;
import lombok.Setter;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Setter
public class OwnerMainDataFacade {

    @DubboReference
    protected IOwnerMainDataApi iOwnerMainDataApi;

    public Collection<OwnerMainDataDTO> getOwners(Collection<String> ownerCodes) {
        return iOwnerMainDataApi.getOwners(ownerCodes);
    }
}
