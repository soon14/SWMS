package com.swms.wms.basic.warehouse.domain.entity;

import lombok.Data;

@Data
public class Warehouse {

    private Long id;
    // unique identifier
    private String warehouseCode;
    private String warehouseName;

    private String area;
    private String capacity;

    private String manager;
    private String managerTel;
    private String managerMail;
    private String managerFax;

    private String province;
    private String city;
    private String district;
    private String address;

    private String remark;

    private boolean inboundNeedReceive;
    private boolean inboundNeedAccept;

    /**
     * 批次属性配置编码
     */
    private String batchAttributeConfigCode;

    private boolean outboundOrderAutoSchedule;

    private boolean deleted;
    private boolean enable;
}
