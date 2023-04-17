package com.swms.wms.task.infrastructure.persistence.po;

import lombok.Data;

@Data
public class TransferContainerPO {

    private Long id;
    private String transferContainerCode;
    private String stationCode;
    private String putWallSlotCode;

    private Integer index;
    private Integer total;

    private String destination;
}
