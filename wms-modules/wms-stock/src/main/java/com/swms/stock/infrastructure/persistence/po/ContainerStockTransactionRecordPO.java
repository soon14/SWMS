package com.swms.stock.infrastructure.persistence.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("container_stock_transaction_record")
public class ContainerStockTransactionRecordPO {
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

    private Boolean processed;

    private Long version;
}
