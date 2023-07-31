package com.swms.wms.stock.domain.entity;

import com.swms.utils.base.BaseUserPO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ContainerStockTransaction {

    private Long id;
    private Long containerStockId;
    private Long skuBatchAttributeId;

    private String sourceContainerCode;
    private String sourceContainerSlotCode;

    private String targetContainerCode;
    private String targetContainerSlotCode;

    private String orderNo;

    private Long taskId;

    private Integer transferQty;

    private Long version;

}
