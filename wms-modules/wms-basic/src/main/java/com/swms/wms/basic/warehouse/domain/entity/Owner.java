package com.swms.wms.basic.warehouse.domain.entity;

import lombok.Data;

@Data
public class Owner {
    private Long id;

    // unique identifier
    private String ownerCode;
    private String ownerName;
}
