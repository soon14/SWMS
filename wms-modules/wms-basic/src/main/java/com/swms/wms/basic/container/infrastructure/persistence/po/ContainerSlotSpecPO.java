package com.swms.wms.basic.container.infrastructure.persistence.po;

import lombok.Data;

@Data
public class ContainerSlotSpecPO {

    private Long id;

    private String containerSpecCode;

    // unique identifier
    private String containerSlotSpecCode;

    private String face;

    private Integer length;
    private Integer width;
    private Integer height;
    private Long volume;

    private Integer level;
    private Integer bay;

    private Long parentId;
}
