package com.swms.mdm.main.data.domain.entity;

import com.swms.mdm.api.main.data.constants.OwnerTypeEnum;
import com.swms.mdm.api.main.data.dto.AddressDTO;
import com.swms.mdm.api.main.data.dto.ContactorDTO;
import lombok.Data;

@Data
public class OwnerMainData {
    private Long id;

    // unique identifier
    private String ownerCode;
    private String ownerName;

    private OwnerTypeEnum ownerType;

    private ContactorDTO contactor;

    private AddressDTO address;

    private Long version;
}
