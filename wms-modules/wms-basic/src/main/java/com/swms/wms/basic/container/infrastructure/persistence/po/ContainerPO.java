package com.swms.wms.basic.container.infrastructure.persistence.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.swms.wms.api.basic.constants.ContainerStatusEnum;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName(value = "container", autoResultMap = true)
public class ContainerPO {

    private Long id;

    //unique identifier
    private String containerCode;

    private String containerSpecCode;

    private String warehouseCode;
    private String warehouseAreaCode;
    private String warehouseLogicCode;
    private String locationCode;
    private String locationType;

    private BigDecimal occupationRatio;

    private boolean empty;
    private boolean lock;

    /**
     * container is or not open
     */
    private boolean open;

    private Integer emptySlotNum;

    private ContainerStatusEnum containerStatus;

    private Long version;
}
