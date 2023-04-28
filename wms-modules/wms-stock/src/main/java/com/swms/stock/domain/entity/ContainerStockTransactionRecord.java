package com.swms.stock.domain.entity;

import com.swms.utils.exception.WmsException;
import lombok.Data;

@Data
public class ContainerStockTransactionRecord {

    private Long id;
    private Long containerStockId;
    private Long batchAttributeId;

    private String sourceContainerCode;
    private String sourceContainerSlotCode;

    private String targetContainerCode;
    private String targetContainerSlotCode;

    private String orderNo;

    private Long taskId;

    private Integer transferQty;

    private boolean processed;

    private Long version;

    public void process() {
        if (processed) {
            throw new WmsException("this record has been processed");
        }

        this.processed = true;
    }
}
