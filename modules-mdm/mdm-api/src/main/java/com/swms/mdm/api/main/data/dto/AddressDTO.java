package com.swms.mdm.api.main.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {

    private String country;
    private String province;
    private String city;
    private String district;
    private String address;
}
