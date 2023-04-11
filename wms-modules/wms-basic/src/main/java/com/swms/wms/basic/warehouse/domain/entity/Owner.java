package com.swms.wms.basic.warehouse.domain.entity;

import lombok.Data;

@Data
public class Owner {
    private Long id;

    // unique identifier
    private String ownerCode;
    private String ownerName;


    private String person;
    private String tel;
    private String fax;
    private String email;
    private String website;

    private String province;
    private String city;
    private String district;
    private String address;
    private String remark;

    private String batchAttributeConfigCode;

    private boolean deleted;
    private boolean enable;
}
