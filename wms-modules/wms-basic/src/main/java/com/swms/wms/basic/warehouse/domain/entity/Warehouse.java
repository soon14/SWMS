package com.swms.wms.basic.warehouse.domain.entity;

import com.swms.wms.api.basic.dto.AddressDTO;
import com.swms.wms.api.basic.dto.ManagerDTO;
import lombok.Data;

@Data
public class Warehouse {

    private Long id;
    // unique identifier
    private String warehouseCode;
    private String warehouseName;

    private String area;
    private String capacity;

    private ManagerDTO manager;

    private AddressDTO address;

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

    private Long version;
}
