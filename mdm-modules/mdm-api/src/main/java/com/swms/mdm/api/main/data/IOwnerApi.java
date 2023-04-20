package com.swms.mdm.api.main.data;

import com.swms.mdm.api.main.data.dto.OwnerMainDataDTO;

public interface IOwnerApi {

    void createOwner(OwnerMainDataDTO ownerMainDataDTO);

    void updateOwner(OwnerMainDataDTO ownerMainDataDTO);

    OwnerMainDataDTO getOwner(String ownerCode);
}
