package com.swms.mdm.main.data.domain.entity;

import lombok.Data;

@Data
public class Sku {

    private Long id;

    /**
     * skuCode + ownerCode union unique identifier
     */
    private String skuCode;
    private String ownerCode;

    private String skuName;

    private Long version;
}
