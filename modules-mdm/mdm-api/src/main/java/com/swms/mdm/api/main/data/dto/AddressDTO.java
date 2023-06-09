package com.swms.mdm.api.main.data.dto;

import lombok.Data;

@Data
public class AddressDTO {

    private String country;
    private String province;
    private String city;
    private String district;
    private String address;
}
