package com.swms.wms.api.basic.dto;

import lombok.Data;

@Data
public class AddressDTO {
    private String province;
    private String city;
    private String district;
    private String address;
}
