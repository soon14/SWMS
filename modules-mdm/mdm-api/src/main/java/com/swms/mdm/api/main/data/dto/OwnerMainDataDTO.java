package com.swms.mdm.api.main.data.dto;

import com.swms.mdm.api.main.data.constants.OwnerTypeEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OwnerMainDataDTO {
    private Long id;

    @NotEmpty
    private String ownerCode;
    @NotEmpty
    private String ownerName;

    @NotNull
    private OwnerTypeEnum ownerType;

    private ContactorDTO contactorDTO;

    private AddressDTO addressDTO;

    private Long version;
}
