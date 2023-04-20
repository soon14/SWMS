package com.swms.mdm.api.main.data.dto;

import com.swms.mdm.api.main.data.constants.OwnerTypeEnum;
import lombok.Data;

@Data
public class OwnerMainDataDTO {
    private Long id;

    // unique identifier
    private String ownerCode;
    private String ownerName;

    private OwnerTypeEnum ownerType;

    private ContactorDTO contactor;

    private AddressDTO address;

}
