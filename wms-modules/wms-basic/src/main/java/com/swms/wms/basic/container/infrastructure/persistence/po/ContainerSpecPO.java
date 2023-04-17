package com.swms.wms.basic.container.infrastructure.persistence.po;

import com.swms.wms.api.basic.constants.ContainerTypeEnum;
import lombok.Data;

@Data
public class ContainerSpecPO {

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
