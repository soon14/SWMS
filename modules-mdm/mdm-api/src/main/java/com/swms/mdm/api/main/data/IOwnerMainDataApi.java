package com.swms.mdm.api.main.data;

import com.swms.mdm.api.main.data.dto.OwnerMainDataDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

public interface IOwnerMainDataApi {

    void createOwner(@Valid OwnerMainDataDTO ownerMainDataDTO);

    void updateOwner(@Valid OwnerMainDataDTO ownerMainDataDTO);

    OwnerMainDataDTO getOwner(@NotEmpty String ownerCode);
}
