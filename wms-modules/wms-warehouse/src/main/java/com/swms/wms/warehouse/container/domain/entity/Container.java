package com.swms.wms.warehouse.container.domain.entity;

import com.swms.wms.api.warehouse.constants.ContainerStatusEnum;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Container {

    private Long id;

    //unique identifier
    private String containerCode;

    private String containerSpecCode;

    private String warehouseCode;
    private String warehouseAreaCode;
    private String warehouseLogicCode;

    private String locationCode;

    private BigDecimal occupationRatio;

    private boolean empty;
    private boolean locked;

    private Integer emptySlotNum;

    private ContainerStatusEnum containerStatus;
}
