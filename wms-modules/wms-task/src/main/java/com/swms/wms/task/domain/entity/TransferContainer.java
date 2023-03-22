package com.swms.wms.task.domain.entity;

import lombok.Data;

@Data
public class TransferContainer {

    private Long id;
    private String transferContainerCode;
    private String stationCode;
    private String putWallSlot;

    private Integer index;
    private Integer total;

    private String destination;
}
