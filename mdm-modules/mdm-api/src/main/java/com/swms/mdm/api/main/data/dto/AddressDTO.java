package com.swms.mdm.api.main.data.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AddressDTO {

    private String country;
    private String province;
    private String city;
    private String district;
    private String address;
}
