package com.swms.wms.warehouse.container.domain.entity;

import com.swms.wms.api.warehouse.constants.ContainerTypeEnum;
import lombok.Data;

@Data
public class ContainerSpec {

    private Long id;

    // unique identifier
    private String containerSpecCode;

    private Integer length;
    private Integer width;
    private Integer height;
    private Long volume;

    private Integer containerSlotNum;

    private String description;

    private ContainerTypeEnum containerType;

}
